# Add a New User to StockKeep Testing

This guide shows you how to add a new user to Firebase App Distribution so they can receive StockKeep updates on their phone.

## Prerequisites

- Access to the Firebase Console for the StockKeep project
- The email address of the person you want to add

## Steps

1. Go to the [Firebase Console](https://console.firebase.google.com/) and select the StockKeep project

2. Navigate to **App Distribution** in the left sidebar (under Release & Monitor)

3. Click on the **Testers & Groups** tab

4. Click **Add testers**

5. Enter the email address of the new user and click **Add testers**

6. The user will receive an email invitation from Firebase

7. The user must accept the invitation by:
   - Clicking the link in the email
   - Following the prompts to install the Firebase App Tester app (or downloading directly)
   - Logging in with their Google account

8. Once accepted, the user will automatically receive notifications when new versions are available

## If the User Doesn't Receive the Invitation

- Check their spam/junk folder
- Verify the email address was entered correctly
- Resend the invitation from Firebase Console → App Distribution → Testers & Groups

## Next Steps

After adding the user, they can install StockKeep by:
- Opening the Firebase App Tester app
- Finding StockKeep in the list
- Downloading and installing the latest version

> **Note:** New users will only receive updates for builds created after they were added. For existing builds, they'll need to manually download from Firebase App Distribution.
