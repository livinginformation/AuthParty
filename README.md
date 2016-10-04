# AuthParty

Interaction flow:

1. The user creates a named or numeric asset A. 
2. The user goes to a website that they want to register an account for
3. The user goes to the registration page, which provides them with a field for a username, and for their asset name/number (possibly not even that, I think we could probably have a way in-app to send the registering asset to the submission page, as well as the other information. That would get rid of the need to type an unwieldy numeric asset name...hm..)
4. The user enters their desired username, and submits it
5. The website displays a QR code containing that username, a challenge string (to prevent replay attacks), a control code, and an API endpoint
6. The user scans the QR with their phone
7. The website creates an association in their database between A and the newly created user account
8. (normal log in stuff that we just did)
9. The user repeats step 2 through step 8 on a second (or a third, or a fourth) website

