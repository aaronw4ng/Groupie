<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../styles/index.css" />
    <link rel="stylesheet" href="../styles/account.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <title>groupie | make account</title>
</head>
<body>
    <div class="header">
        <h1>groupie</h1>
    </div>
    <div class="account-page">
        <h1>Create Account</h1>
        <p>Create an account to get started with the scheduling app!</p>
        <form class="account-form">
            <p class="input-header">Username</p>
            <input class="field" type="text" id="input-username" placeholder="Username">
            <p class="input-header">Password</p>
            <input class="field" type="password" id="input-password" placeholder="Password">
            <div class="instructions">
                <p>Should contain at least 8 characters</p>
            </div>
            <p class="input-header">Confirm Password</p>
            <input class="field" type="password" id="re-input-password" placeholder="Confirm Password">
            
            <div class="warning-message"></div>

            <div class="btn-row">
                <button id="btn-create-account" type="button" onclick="handleRegisterClick(event)">Create Account</button>
                <button id="btn-back">Cancel</button>
            </div>
            
        </form>
    </div>

    <div id="footer">
        <p>team 27</p>
    </div>

    <script src="../scripts/register.js"></script>
</body>
    <jsp:include page="autologout.jsp"></jsp:include>
</html>