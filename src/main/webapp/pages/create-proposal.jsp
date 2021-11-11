<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../styles/index.css" />
    <link rel="stylesheet" href="../styles/proposal.css" />
    <title>groupie | proposal</title>
</head>
<body>
  <div class="header">
    <h1>groupie</h1>
    <div class="header-links">
      <a href="#" class="highlight-link">Proposals</a>
      <a href="../index.jsp">Logout</a>

    </div>
  </div>

  <div id="create-proposal-page">
    <h1>Propose an Event</h1>
    <h2>Fill out the following fields to propose group dates to your friends.</h2>
    <form id="proposal-form">
      <p class="input-header title">proposal name</p>
      <input class="field-input" type="text" id="input-proposal-name" placeholder="Name"><br/>
      <p class="input-header">proposed events</p>
      <button id="btn-event-find" onclick="handleFindEventClick(event)">+ add event</button>
      <p class="input-header">invite users</p>
      <input class="field-input" id="input-invitees" placeholder="Search">
      <div class="btn-row">
        <button name="btn-send-proposal" class = "principal">Send Proposal</button>
        <button name="btn-save-draft" class ="principal">Save Draft</button>
      </div>
    </form>
  </div>

  <div id="footer">
    <p>team 27</p>
  </div>

  <script src="../scripts/create-proposal.js"></script>
</body>
  <jsp:include page="autologout.jsp"></jsp:include>
</html>