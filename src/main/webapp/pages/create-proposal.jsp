<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../styles/index.css" />
    <link rel="stylesheet" href="../styles/proposal.css" />
    <script src="https://kit.fontawesome.com/d4a13a138b.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
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
      <!-- Container for selected events display -->
      <div class="events-container">

      </div>
      <button id="btn-event-find" onclick="handleFindEventClick(event)">+ add event</button>

      <!-- USER INVITE -->
      <p class="input-header">invite users</p>
      <div id="added-user-container">

      </div>
      <input class="field-input" id="user-search-input" placeholder="username" onkeyup="handleInputChange(event)">
      <div id="user-results-container">
        <div class="user-card">
          <p><i class="fas fa-users"></i> username</p>
        </div>
      </div>

      <!-- PROPOSAL BUTTONS -->
      <div class="btn-row">
        <button onclick="handleCreateProposalClick(event)" name="btn-send-proposal" class = "principal">Send Proposal</button>
        <button name="btn-save-draft" class ="principal">Save Draft</button>
      </div>
    </form>
  </div>

  <div id="footer">
    <p>team 27</p>
  </div>

  <script src="../scripts/create-proposal.js"></script>
  <script src="../scripts/user-search.js"></script>
</body>
  <!-- <jsp:include page="autologout.jsp"></jsp:include> -->
</html>