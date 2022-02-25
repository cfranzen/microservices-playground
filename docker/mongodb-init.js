graylog = db.getSiblingDB('graylog');
graylog.createUser(
  {
    user: "graylog",
    pwd: "graylog",
    roles: [
      { role: "dbOwner", db: "graylog" }
    ]
  }
);

notification = db.getSiblingDB('notification');
notification.createUser(
  {
    user: "notification",
    pwd: "notification",
    roles: [
      { role: "dbOwner", db: "notification" }
    ]
  }
);