<!DOCTYPE html>
<html>

<head>
  <title>groupie | privacy settings</title>
  <link rel="stylesheet" href="../styles/index.css" />
  <link rel="stylesheet" href="../styles/proposal.css" />
  <script src="https://kit.fontawesome.com/d4a13a138b.js" crossorigin="anonymous"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;500;800&display=swap');
    </style>
</head>

<body>
  <div id="page-container">
    <div class="header">
      <h1>groupie</h1>
      <div class="header-links">
        <a href="view-proposals.jsp">View Proposals</a>
        <a href="view-proposals.jsp">Create Proposal</a>
        <a href="#" class="highlight-link">Privacy</a>
        <a href="create-proposal.jsp">Logout</a>
      </div>
    </div>

    <!-- Blocked Users Container -->
    <div>
    <h1>manage privacy settings</h1>
      <div class="border">
        <h2>blocked users</h2>
        <p> select a user in the list to unblock them. </p>
        <div id="blocked-users-container"></div>
       <button onclick="handleUnblock()">unblock</button>
      </div>

      <div class="border">
        <h2>block a new user</h2>
        <p> enter a username and click the button to unblock them. </p>
        <div id="block-users-container">
            <input id="block-users-field" type="text" onKeyPress="setBlockOption(event)" onKeyUp="setBlockOption(event)"></input>
        </div>
       <button onclick="handleBlock()">block</button>
      </div>

    <!-- Availability Container -->
    <div>
      <h1>availability</h1>
      <div class="border">
          <div class="btn-view">
            <button class="selected">available</button>
            <button>unavailable</button>
          </div>
        <h3>Set Time: </h3>
      </div>
    </div>

</div>
  <script src="../scripts/privacy-settings.js"></script>
</body>
  <jsp:include page="autologout.jsp"></jsp:include>
</html>