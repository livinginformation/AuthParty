Setup:

1. Have Python and Pip installed
2. pip install Flask
3. install sqlite

4.OSX:
	1. sudo easy_install virtualenv
	2. virtualenv [app name]
	3. virtualenv -p /usr/bin/python env
	4. source env/bin/activate
	5. touch requirements.txt
	6. nano requirements.txt
	7. Add "Flask" to top (no quotes)
	8. pip install -r requirements.txt
	9. python appname.py



Query:

curl -H "Content-Type: application/json" -X POST -d '{"signature":"thisisasignature","challenge":"thisisachallenge", "assetname":"thisisanassetname","username":"username", "public_key":"thisisapublickey"}' http://localhost:5000/register