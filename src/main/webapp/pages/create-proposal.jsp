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
      <a id="view-proposals-btn" href="view-proposals.jsp">View Proposals</a>
      <a id="create-proposal-btn" href="#" class="highlight-link">Create Proposal</a>
      <a id="privacy-settings-btn" href="privacy-settings.jsp">Privacy Settings</a>
      <a id="logout-btn" onclick="leave_site()">Logout</a>
    </div>
  </div>
  <script>
    function leave_site() {
        sessionStorage.clear()
        document.location.href="../index.jsp"
        console.log("session ended " + sessionStorage.getItem("username"))
    }
  </script>

  <div id="create-proposal-page">
    <h1>create proposal</h1>

    <form id="proposal-form">
      <!-- Proposal Name -->
      <p class="input-header" id="proposal-name-header">proposal name</p>
      <input class="field-input" type="text" id="input-proposal-name" value="new proposal" onblur="handleProposalNameBlur(event)">
      <i class="fas fa-pen"></i>
      <hr>

      <p class="input-header">proposed events</p>
      <!-- Container for selected events display -->
      <div class="events-container">

      </div>
      <button id="btn-event-find" onclick="handleFindEventClick(event)">+ add event</button>

      <hr>

      <!-- USER INVITE -->
      <p class="input-header">invited users</p>
      <div id="added-user-container">

      </div>
      <div class="form-col">
        <button id="btn-add-users" onclick="handleAddUsersClick(event)">+ add users</button>
        <input class="field-input" id="user-search-input" placeholder="search username" onkeyup="handleInputChange(event)" onfocus="handleUserInputFocus(event)">
      </div>
      <div id="user-results-container">
        
      </div>

      <hr>

      <!-- PROPOSAL BUTTONS -->
      <div class="btn-row">
        <button onclick="handleCreateProposalClick(event)" id="btn-send-proposal" class="principal">send proposal</button>
        <button onclick="handleSaveDraftClick(event)" id="btn-save-draft" class="principal">save draft</button>
      </div>
    </form>
  </div>

  <div id="footer">
    <p>team 27</p>
  </div>
  <jsp:include page="autologout.jsp"></jsp:include>
  <script src="../scripts/autologout.js"></script>
  <script src="../scripts/create-proposal.js"></script>
  <script src="../scripts/user-search.js"></script>
</body>
</html>