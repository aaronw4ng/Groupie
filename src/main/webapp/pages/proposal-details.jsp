<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../styles/index.css" />
    <link rel="stylesheet" href="../styles/proposal-details.css" />
    <script src="https://kit.fontawesome.com/d4a13a138b.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <title>groupie | proposal details</title>
</head>
<body>
  <div class="header">
    <h1>groupie</h1>
    <div class="header-links">
      <a href="#" class="highlight-link">Proposals</a>
      <a href="../index.jsp">Logout</a>

    </div>
  </div>

  <div id="proposal-details-page">
      <div class="details-row">
          <h1 id="page-title"><i class="fas fa-info-circle"></i> proposal details</h1>
          <div class="btn-back-container">
              <p>back to your groupies</p>
              <button id="btn-back" onclick="handleBackBtnClick(event)"><i class="fas fa-undo-alt"></i></button>
          </div>
      </div>
      <h1 class="section-title">title</h1>
      <h1 id="proposal-name">sample name</h1>

      <hr>
      <!-- EVENTS -->
      <div class="page-section">
        <h1 class="section-title">events</h1>
        <p class="section-description">click to view event details</p>
        <div class="events-container">
          <div data-event-id="1" class="event-card">
            <h1>event one</h1>
            <button id="btn-delete-event-1" class="btn-delete" onclick="handleDeleteClick(event, 'event')">X</button>
          </div>
          <div data-event-id="2" class="event-card">
            <h1>event two</h1>
            <button id="btn-delete-event-2" class="btn-delete" onclick="handleDeleteClick(event, 'event')">X</button>
          </div>
        </div>
        
      </div>
      
      <hr>
      <!-- USERS -->
      <div class="page-section">
        <h1 class="section-title">users</h1>
        <p class="section-description">see who's in your groupie</p>
        <div class="users-container">
          <div class="user-card">
            <p id="proposal-host-header"><i class="fas fa-crown"></i> proposal host</p>
            <h1>user one</h1>
          </div>
          <div data-user-id="2" class="user-card">
            <h1>user two</h1>
            <button id="btn-delete-user-2" class="btn-delete" onclick="handleDeleteClick(event, 'user')">X</button>
          </div>
        </div>

      </div>

      <div id="delete-container">
        <button id="btn-delete-proposal" onclick="handleDeleteProposalClick(event)">delete proposal</button>
      </div>
  </div>

  

  <div id="footer">
    <p>team 27</p>
  </div>
  <script src="../scripts/autologout.js"></script>
  <script src="../scripts/proposal-details.js"></script>
</body>
  <!-- <jsp:include page="autologout.jsp"></jsp:include> -->
</html>