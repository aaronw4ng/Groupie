<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../styles/proposal.css" />
    <link rel="stylesheet" href="../styles/index.css" />
    <title>Create Proposal | GDP</title>
    <style>
    @import url('https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;800&display=swap');
    </style>
</head>
<body>
  <div class="header">
    <h1>grouping</h1>
    <div class="header-links">
      <a href="#" class="highlight-link">Proposals</a>
      <a href="../index.jsp">Logout</a>

    </div>
  </div>

  <div id="create-proposal-page">
    <h1>Propose an Event</h1>
    <h2>Fill out the following fields to propose group dates to your friends.</h2>
    <form id="proposal-form">
      <p class="input-header title">Proposal Name</p>
      <input class="field-input" type="text" id="input-proposal-name" placeholder="Name"><br/>
      <p class="input-header">List of Proposed Events</p>
      <p>events: </p> <button id="btn-event-find" onclick="handleFindEventClick(event)">+ add event</button>
      <p class="input-header">Search for Users to Invite</p>
      <input class="field-input" id="input-invitees" placeholder="Search"><br/>
      <br/><button name="btn-send-proposal" class = "principal">Send Proposal</button>
      <button name="btn-save-draft" class ="principal">Save Draft</button>
    </form>
  </div>

  <div id="footer">
    <p>team 27</p>
  </div>

  <script src="../scripts/create-proposal.js"></script>

</body>
</html>