# QOTD Server

This is just a fun project to create a QOTD (Quote of the Day) server that will provide quotes using the QOTD Protocol RFC-865 (https://tools.ietf.org/html/rfc865).

It will get quotes from one of the inputs below, in this order:

- A text defined in a properties file. The contents will be read, added to the database and sent via the protocol;

- Fetch online a quote, send it via the protocol and save it in the database;

- Using randomly one of the quotes from the database.
