<%@ page import="vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.entities.Role" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.entities.Log" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        .list {
            list-style-type: none;
            margin: 0;
            padding: 0;
        }

        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            color: #333;
            background-color: #f4f4f4;
        }

        .container {
            width: 90%;
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }

        h1, h2 {
            margin-bottom: 20px;
        }

        .card {
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        th, td {
            text-align: left;
            padding: 12px;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }

        button {
            background-color: #4CAF50;
            border: none;
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
            border-radius: 4px;
        }

        button.delete {
            background-color: #f44336;
        }

        button.edit {
            background-color: #008CBA;
        }

        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.4);
        }

        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
            max-width: 500px;
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }

        form {
            display: flex;
            flex-direction: column;
        }

        label {
            margin-top: 10px;
        }

        input, select {
            margin-bottom: 10px;
            padding: 5px;
        }

        @media (max-width: 768px) {
            .container {
                width: 95%;
            }

            table {
                font-size: 14px;
            }

            button {
                padding: 8px 16px;
                font-size: 14px;
            }
        }

        .btn-logout {
            background-color: darkred;
            color: white;
            width: 50%;
        }

        .form-logout {
            display: flex;
            justify-content: center;
            margin-top: 20px;
            align-items: center;
        }
    </style>
</head>

<%
    List<Role> roles = (List<Role>) session.getAttribute("roles");
    List<Log> logs = (List<Log>) request.getAttribute("logs");
%>

<body>
<div class="container">
    <p>Xin chao, ${sessionScope.user.fullName}!</p>
    <p>Vai tro cua ban:</p>
    <ul class="list">
        <% for (Role role : roles) { %>
        <li><%= role.getRoleName() %>
        </li>
        <% } %>
    </ul>
    <p></p>
    <h1>Dashboard</h1>
    <div id="adminPanel" style="display:none;">
        <div class="card">
            <h2>Quan ly tai khoan</h2>
            <button id="addUserBtn">Them nguoi dung</button>
            <table id="userTable">
                <tr>
                    <th>Log ID</th>
                    <th>Login Time</th>
                    <th>Logout Time</th>
                </tr>


            </table>
        </div>


        <div class="card">
            <h2>Log</h2>
            <table id="loginLogTable">
                <thead>
                <tr>
                    <th>Thoi gian</th>
                    <th>Nguoi dung</th>
                    <th>Trang thai</th>
                </tr>
                </thead>
                <tbody>
                <% for (Log log : logs) { %>
                <tr>
                    <td><%= log.getAccountId() %>
                    </td>
                    <td><%= log.getLoginTime() %>
                    </td>
                    <td><%= log.getNotes() %>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
    <div id="userPanel" style="display:none;">
        <div class="card">
            <h2>Thong tin ca nhan</h2>
            <p><strong>Ten:</strong> <span id="userName"></span></p>
            <p><strong>Email:</strong> <span id="userEmail"></span></p>
            <p><strong>Vai tro:</strong> <span id="userRole"></span></p>
            <h3>Quyen:</h3>
            <ul id="userPermissions"></ul>
        </div>
    </div>
</div>

<div id="userModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <h2 id="modalTitle">Them/Sua nguoi dung</h2>
        <form id="userForm">
            <input type="hidden" id="userId">
            <label for="name">Ten:</label>
            <input type="text" id="name" required>
            <label for="email">Email:</label>
            <input type="email" id="email" required>
            <label for="role">Vai tro:</label>
            <select id="role">
                <option value="User">User</option>
                <option value="Manager">Manager</option>
                <option value="Admin">Admin</option>
            </select>
            <button type="submit">Luu</button>
        </form>
    </div>
</div>

<%--button logout--%>
<form action="controlServlet" method="post" class="form-logout">
    <input type="hidden" name="action" value="logout">
    <button class="btn-logout" type="submit">Logout</button>
</form>

<script !src="">
    // Mock data
    let users = [
        {id: 1, name: 'Admin User', email: 'admin@example.com', role: 'Admin'},
        {id: 2, name: 'Manager User', email: 'manager@example.com', role: 'Manager'},
        {id: 3, name: 'Regular User', email: 'user@example.com', role: 'User'},
    ];

    let loginLogs = [
        {time: '2023-05-20 10:30:00', user: 'admin@example.com', status: 'Success'},
        {time: '2023-05-20 11:45:00', user: 'manager@example.com', status: 'Success'},
        {time: '2023-05-20 14:15:00', user: 'user@example.com', status: 'Failed'},
    ];

    const currentUser = {
        id: 1, // Changed to Admin for demonstration
        name: 'Admin User',
        email: 'admin@example.com',
        role: 'Admin',
        permissions: ['manage_users', 'view_logs']
    };

    // DOM elements
    const adminPanel = document.getElementById('adminPanel');
    const userPanel = document.getElementById('userPanel');
    const userTable = document.getElementById('userTable');
    const loginLogTable = document.getElementById('loginLogTable');
    const addUserBtn = document.getElementById('addUserBtn');
    const userModal = document.getElementById('userModal');
    const userForm = document.getElementById('userForm');
    const modalTitle = document.getElementById('modalTitle');
    const closeModal = document.querySelector('.close');

    // Functions
    <%--function renderUserTable() {--%>
    <%--    const tbody = userTable.querySelector('tbody');--%>
    <%--    tbody.innerHTML = '';--%>
    <%--    users.forEach(user => {--%>
    <%--        const row = `--%>
    <%--                <tr>--%>
    <%--                    <td>${user.name}</td>--%>
    <%--                    <td>${user.email}</td>--%>
    <%--                    <td>${user.role}</td>--%>
    <%--                    <td>--%>
    <%--                        <button class="edit" data-id="${user.id}">Sửa</button>--%>
    <%--                        <button class="delete" data-id="${user.id}">Xóa</button>--%>
    <%--                    </td>--%>
    <%--                </tr>--%>
    <%--            `;--%>
    <%--        tbody.insertAdjacentHTML('beforeend', row);--%>
    <%--    });--%>
    <%--}--%>

    function renderLoginLogTable() {
        const tbody = loginLogTable.querySelector('tbody');
        tbody.innerHTML = '';
        loginLogs.forEach(log => {
            const row = `
                    <tr>
                        <td>${log.time}</td>
                        <td>${log.user}</td>
                        <td>${log.status}</td>
                    </tr>
                `;
            tbody.insertAdjacentHTML('beforeend', row);
        });
    }


    function renderUserInfo() {
        document.getElementById('userName').textContent = currentUser.name;
        document.getElementById('userEmail').textContent = currentUser.email;
        document.getElementById('userRole').textContent = currentUser.role;
        const permissionsList = document.getElementById('userPermissions');
        permissionsList.innerHTML = '';
        currentUser.permissions.forEach(permission => {
            const li = document.createElement('li');
            li.textContent = permission;
            permissionsList.appendChild(li);
        });
    }

    function showModal(title, user = null) {
        modalTitle.textContent = title;
        if (user) {
            document.getElementById('userId').value = user.id;
            document.getElementById('name').value = user.name;
            document.getElementById('email').value = user.email;
            document.getElementById('role').value = user.role;
        } else {
            userForm.reset();
            document.getElementById('userId').value = '';
        }
        userModal.style.display = 'block';
    }

    // Event Listeners
    addUserBtn.addEventListener('click', () => showModal('Thêm người dùng'));

    closeModal.addEventListener('click', () => {
        userModal.style.display = 'none';
    });

    userForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const userId = document.getElementById('userId').value;
        const user = {
            id: userId ? parseInt(userId) : users.length + 1,
            name: document.getElementById('name').value,
            email: document.getElementById('email').value,
            role: document.getElementById('role').value
        };

        if (userId) {
            // Update existing user
            const index = users.findIndex(u => u.id === parseInt(userId));
            users[index] = user;
        } else {
            // Add new user
            users.push(user);
        }

        renderUserTable();
        userModal.style.display = 'none';
    });

    userTable.addEventListener('click', (e) => {
        if (e.target.classList.contains('edit')) {
            const userId = parseInt(e.target.getAttribute('data-id'));
            const user = users.find(u => u.id === userId);
            showModal('Sửa người dùng', user);
        } else if (e.target.classList.contains('delete')) {
            const userId = parseInt(e.target.getAttribute('data-id'));
            users = users.filter(u => u.id !== userId);
            renderUserTable();
        }
    });

    // Initialize
    if (currentUser.role === 'Admin') {
        adminPanel.style.display = 'block';
        renderUserTable();
        renderLoginLogTable();
    } else {
        userPanel.style.display = 'block';
        renderUserInfo();
    }
</script>
</body>
</html>