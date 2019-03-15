CREATE TABLE user_subscriptions (
  channel_id int8 not null REFERENCES usr,
  subscriber_id int8 not null REFERENCES usr,
  PRIMARY KEY (channel_id, subscriber_id)
)