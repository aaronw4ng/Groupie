<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="./styles/index.css" />
    <script src="./scripts/login.js"></script>
</head>
    <body>
        <div id="login-page">
            <h1>Login</h1>
            <form id="login-form">
                <p class="input-header">Username</p>
                <input class="field-input" type="text" name="input-username" placeholder="Username">
                <p class="input-header">Password</p>
                <input class="field-input" type="password" name="input-password" placeholder="Password">
                <button onclick="handleLoginClick(event)" id="btn-login">LOGIN</button>
            </form>
        </div>
    </body>
</html>