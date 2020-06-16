#!/bin/sh

echo "Stating deploy."
echo $CIRCLE_BRANCH

if [ "$CIRCLE_BRANCH" = "dev" ]; then


  if [ -z "$FIREBASE_APP_ID_STAG" && -z "$APK_PATH_STAG" && -z $FIREBASE_TOKEN]; then
    echo "Staging env variables missing. Exiting."
    exit 1
  fi

  echo "Deploying application to Staging"

  bundle exec fastlane distribute app_id:$FIREBASE_APP_ID_STAG apk_path:$APK_PATH_STAG firebase_token:$FIREBASE_TOKEN groups:$TESTERS_STAG

elif [ "$CIRCLE_BRANCH" = "master" ]; then

  if [ -z "$FIREBASE_APP_ID_PROD" && -z "$APK_PATH_PROD" && -z $FIREBASE_TOKEN]; then
    echo "Production env variables missing. Exiting."
    exit 1
  fi

  echo "Deploying application to Production environment"

  bundle exec fastlane distribute app_id:$FIREBASE_APP_ID_PROD apk_path:$APK_PATH_PROD firebase_token:$FIREBASE_TOKEN groups:$TESTERS_PROD
fi
