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