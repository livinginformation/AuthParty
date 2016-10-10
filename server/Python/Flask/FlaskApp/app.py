from flask import Flask, render_template, request, json, g
import sqlite3

# from werkzeug import generate_password_hash, check_password_hash
DATABASE = 'authparty.db'
app = Flask(__name__)


def get_db():
    db = getattr(g, '_database', None)
    if db is None:
        db = g._database = sqlite3.connect(DATABASE)
    return db

@app.teardown_appcontext
def close_connection(exception):
    db = getattr(g, '_database', None)
    if db is not None:
        db.close()

def init_db():
    with app.app_context():
        db = get_db()
        with app.open_resource('sp_createUsers.sql', mode='r') as f:
            db.cursor().executescript(f.read())
        db.commit()

        with app.open_resource('sp_createSessions.sql', mode='r') as f:
            db.cursor().executescript(f.read())
        db.commit()


init_db()

@app.route("/")
def main():
    return render_template('index.html')

@app.route('/showSignUp')
def showSignUp():
    return render_template('signup.html')

@app.route('/signUp', methods=['POST'])
def signUp():
    create_user_sql = "INSERT INTO Users (USERNAME, PASSWORD, EMAIL) VALUES (?, ?, ?)"
    # read the posted values from the UI
    _name = request.form['inputName']
    _email = request.form['inputEmail']
    _password = request.form['inputPassword']

    # validate received values
    if _name and _email and _password:
        _db = get_db()
        cur = _db.cursor()
        cur.execute(create_user_sql, (_name, _password, _email))
        _db.commit()

        return json.dumps({'html':'<span>All fields valid!</span>'})

    else:
        return json.dumps({'html':'<span>Enter the required fields</span>'})


if __name__ == "__main__":
    app.run()
