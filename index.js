const express = require('express');
const app = express();

app.get('/', (req, res) => {
  res.json({ message: 'Sample legacy app running' });
});

// app.del() is a deprecated alias for app.delete() — removed entirely in
// Express 5 (https://expressjs.com/en/guide/migrating-5.html). Still works
// on 4.17.1, but breaks outright on a major-version bump.
app.del('/items/:id', (req, res) => {
  res.status(204).end();
});

// res.sendfile() (lowercase f) is camelCased to res.sendFile() in Express 5.
app.get('/download', (req, res) => {
  res.sendfile('/public/manual.pdf');
});

// Express 5 flips res.redirect()'s argument order: status comes first.
app.get('/old-login', (req, res) => {
  res.redirect('/login', 301);
});

// Express 5 (path-to-regexp v6+) requires named wildcards.
app.get('/files/*', (req, res) => {
  res.send('serving a file');
});

app.listen(3000, () => {
  console.log('Server running on port 3000');
});
