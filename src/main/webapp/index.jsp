<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="./styles/index.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="./scripts/login.js"></script>
</head>
    <body>
        <div id="login-page">
            <h1>Login</h1>
            <form id="login-form">
                <p class="input-header">Username</p>
                <input class="field-input" type="text" id="input-username" placeholder="Username">
                <p class="input-header">Password</p>
                <input class="field-input" type="password" id="input-password" placeholder="Password">
                <button onclick="handleLoginClick(event)" id="btn-login">Login</button>
            </form>
        </div>
    </body>
</html>