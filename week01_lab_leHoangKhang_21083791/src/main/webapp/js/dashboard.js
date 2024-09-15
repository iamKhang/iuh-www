// Mock data
let users = [
    { id: 1, name: 'Admin User', email: 'admin@example.com', role: 'Admin' },
    { id: 2, name: 'Manager User', email: 'manager@example.com', role: 'Manager' },
    { id: 3, name: 'Regular User', email: 'user@example.com', role: 'User' },
];

let loginLogs = [
    { time: '2023-05-20 10:30:00', user: 'admin@example.com', status: 'Success' },
    { time: '2023-05-20 11:45:00', user: 'manager@example.com', status: 'Success' },
    { time: '2023-05-20 14:15:00', user: 'user@example.com', status: 'Failed' },
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
function renderUserTable() {
    const tbody = userTable.querySelector('tbody');
    tbody.innerHTML = '';
    users.forEach(user => {
        const row = `
                    <tr>
                        <td>${user.name}</td>
                        <td>${user.email}</td>
                        <td>${user.role}</td>
                        <td>
                            <button class="edit" data-id="${user.id}">Sửa</button>
                            <button class="delete" data-id="${user.id}">Xóa</button>
                        </td>
                    </tr>
                `;
        tbody.insertAdjacentHTML('beforeend', row);
    });
}

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