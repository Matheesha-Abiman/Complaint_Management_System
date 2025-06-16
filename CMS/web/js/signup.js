document.addEventListener('DOMContentLoaded', function () {
    // Toggle password visibility
    document.getElementById('togglePassword').addEventListener('click', function () {
        const passwordInput = document.getElementById('password');
        const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
        passwordInput.setAttribute('type', type);
        this.classList.toggle('bi-eye-slash-fill');
    });

    document.getElementById('toggleConfirmPassword').addEventListener('click', function () {
        const confirmPasswordInput = document.getElementById('confirmPassword');
        const type = confirmPasswordInput.getAttribute('type') === 'password' ? 'text' : 'password';
        confirmPasswordInput.setAttribute('type', type);
        this.classList.toggle('bi-eye-slash-fill');
    });

    // Form validation
    document.querySelector('form').addEventListener('submit', function (e) {
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        const termsAgree = document.getElementById('termsAgree').checked;

        if (password !== confirmPassword) {
            alert('Passwords do not match');
            e.preventDefault();
            return;
        }

        if (password.length < 8) {
            alert('Password must be at least 8 characters long');
            e.preventDefault();
            return;
        }

        if (!termsAgree) {
            alert('You must agree to the terms and conditions');
            e.preventDefault();
        }
    });
});
