<!DOCTYPE html>
<html>
<head>
    <title>Group Date Planner</title>
    <link rel="stylesheet" href="./styles/index.css" />
    <link rel="stylesheet" href="./styles/account.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
    <body>
        <div class="header">
            <h1>groupie</h1>
        </div>
        <div class="account-page">
            <h1>Login</h1>
            <form class="account-form">
                <p class="input-header">Username</p>
                <input class="field" type="text" id="input-username" placeholder="Username">
                <p class="input-header">Password</p>
                <input class="field" type="password" id="input-password" placeholder="Password">
                <div class="warning-message"></div>
                <button onclick="handleLoginClick(event)" id="btn-login">Login</button>
            </form>
            <button id="btn-create-page">Create Account</button>
        </div>

        <div id="footer">
            <p>team 27</p>
        </div>
        <script src="./scripts/login.js"></script>
    </body>
</html>