Authparty has two components: 

1. The server-side login module

2. The client-side login application

They will communicate with each other through the following JSON protocol:

Server -> Client (Login, embedded in QR):

	{
	    "Control_code": "login",
	    "Challenge": challenge_string,
	    "API": api_endpoint,
	    "site_name": website_name
	}

Client -> Server (Login, response)

	{
	    "Challenge": challenge,
	    "Challenge_Signature": signature,
	    "Public_Key": public_key,
	}

Server -> Client (Login, response if >1 valid account)

	{
	    "New_Challenge": new_challenge,
	    "Accounts": ["username1", "username2", ..., "usernameN"]
	    "API": api_endpoint
	}

Client -> Server (Login, response to multiple valid accounts)

	
	{
	    "New_Challenge": new_challenge,
	    "Challenge_Signature": signature,
	    "Username": username
	}

Server -> Client (Success or Failure)

	{
	    "Status": success_or_fail
	}
