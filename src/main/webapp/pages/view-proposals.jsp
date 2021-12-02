<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
  <head>
    <title>groupie | proposals</title>
    <link rel="stylesheet" href="../styles/index.css" />
    <link rel="stylesheet" href="../styles/view-proposals.css" />
    <link href="../lib/main.css" rel="stylesheet" />
    <script src="../lib/main.js"></script>
    <script
      src="https://kit.fontawesome.com/d4a13a138b.js"
      crossorigin="anonymous"
    ></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <style>
      @import url("https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;500;800&display=swap");
    </style>
  </head>

  <body>
    <div id="page-container">
      <div class="header">
        <h1>groupie</h1>
        <div class="header-links">
      <a id="view-proposals-btn" href="#" class="highlight-link">View Proposals</a>
      <a id="create-proposal-btn" href="create-proposal.jsp">Create Proposal</a>
      <a id="privacy-settings-btn" href="privacy-settings.jsp">Privacy Settings</a>
      <a id="logout-btn" href="../index.jsp">Logout</a>
        </div>
      </div>

      <!-- Received Proposals Container -->
      <h1 id="page-header">view proposals</h1>

      <!-- <div id="toggle-proposals-container">
        <p>select your proposals</p>
        <select id="select-proposal" onchange="handleProposalOptionClick(event)">
          <option id="draft-option" class="proposal-option">drafts</option>
          <option id="sent-option" class="proposal-option">sent</option>
          <option id="received-option" class="proposal-option">received</option>
        </select>
      </div> -->

      <div id="proposal-results-container"></div>

      <!-- three buttons: show draft, show finalized, show unfinalized  -->
      <h1 id="page-title">
        <i class="fas fa-info-circle"></i> finalized events
      </h1>
      <div id="calendar"></div>
    </div>
    <div id="footer">
      <p>team 27</p>
    </div>
    <script src="../scripts/view-proposals.js"></script>
  </body>
  <jsp:include page="autologout.jsp"></jsp:include>
</html>
