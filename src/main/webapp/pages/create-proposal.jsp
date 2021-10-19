<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="./styles/proposal.css" />
    <title>Create Proposal | GDP</title>
    <style>
    @import url('https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;800&display=swap');
    </style>
</head>
<body>

<div id="create-proposal-page">
  <h1>Propose an Event</h1>
  <h2>Fill out the following fields to propose group dates to your friends.</h2>
  <form id="proposal-form">
    <p class="input-header title">Proposal Name</p>
    <input class="field-input" type="text" id="input-proposal-name" placeholder="Name"><br/>
    <p class="input-header">List of Proposed Events</p>
    <p>Event 1: <input type="text"> </p>
    <button class="add">+ Another Event </button>
    <p class="input-header">Search for Users to Invite</p>
    <input class="field-input" id="input-invitees" placeholder="Search"><br/>
    <br/><button name="btn-send-proposal" class = "principal">Send Proposal</button>
    <button name="btn-save-draft" class = "principal">Save Draft</button>
  </form>

</body>
</html>