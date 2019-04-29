# QOTD Server

This is just a fun project to create a QOTD (Quote of the Day) server that will provide quotes using the QOTD Protocol RFC-865 (https://tools.ietf.org/html/rfc865).

## About the quotes

### Algorithm

The quotes comes from one of following sources:

- If the database is not reachable...
    - The only quote provided is the default value from properties file;
- If connected to the database... 
    - If the latest timestamp is...
        - today's: serve a random quote;
        - else: fetch quote online...
            - if successful: save it and server it;
            - else: serve a random quote.

### Online source

The quotes are fetched in a website that provides random quotes daily.