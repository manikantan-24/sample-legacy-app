const express = require('express');
const app = express();

app.get('/', (req, res) => {
  res.json({ message: 'Sample legacy app running' });
});

// app.del() is a deprecated alias for app.delete() — removed entirely in
// Express 5 (https://expressjs.com/en/guide/migrating-5.html). Still works
// on 4.17.1, but breaks outright on a major-version bump.
app.delete('/items/:id', (req, res) => {
  res.status(204).end();
});

app.listen(3000, () => {
  console.log('Server running on port 3000');
});
