document.addEventListener('DOMContentLoaded', function() {
    const togglePassword = document.getElementById('togglePassword');
    const passwordInput = document.getElementById('password');

    // Toggle password visibility
    togglePassword.addEventListener('click', function() {
        const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
        passwordInput.setAttribute('type', type);
        this.classList.toggle('bi-eye-slash-fill');
    });

    // Client-side validation
    document.querySelector('form').addEventListener('submit', function(e) {
        const email = document.getElementById('email').value.trim();
        const password = passwordInput.value.trim();

        // Basic validation
        if (!email || !password) {
            alert('Please fill in all fields');
            e.preventDefault();
            return;
        }

        // Email format validation
        if (!validateEmail(email)) {
            alert('Please enter a valid email address');
            e.preventDefault();
            return;
        }
    });

    // Email validation helper
    function validateEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    }
});