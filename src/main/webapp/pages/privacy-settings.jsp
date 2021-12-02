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
      <a id="view-proposals-btn" href="view-proposals.jsp">View Proposals</a>
      <a id="create-proposal-btn" href="create-proposal.jsp">Create Proposal</a>
      <a id="privacy-settings-btn" href="#"  class="highlight-link">Privacy Settings</a>
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
          <div id="avail-status"></div>
          <p>To make yourself unavailable indefinitely, leave time at 0 (measured in hours).</p>
          <div class="btn-view">
            <button id="avail-but" onclick="toggleAvailability(event)">set available</button>
            <button id="unavail-but"  onclick="toggleAvailability(event)">set unavailable</button>
          </div>
        <h3>Set Time: </h3>
    <input type="number" id="hours-unavail" min="0" max="100000">
      </div
    </div>

</div>
  <script src="../scripts/privacy-settings.js"></script>
</body>
  <jsp:include page="autologout.jsp"></jsp:include>
</html>