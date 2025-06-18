document.addEventListener('DOMContentLoaded', function () {
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

    document.querySelector('form').addEventListener('submit', function (e) {
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        const termsAgree = document.getElementById('termsAgree').checked;

        if (password !== confirmPassword) {
            Swal.fire({
                icon: 'error',
                title: 'Oops!',
                text: 'Passwords do not match',
            });
            e.preventDefault();
            return;
        }

        if (password.length < 8) {
            Swal.fire({
                icon: 'warning',
                title: 'Weak Password',
                text: 'Password must be at least 8 characters long',
            });
            e.preventDefault();
            return;
        }

        if (!termsAgree) {
            Swal.fire({
                icon: 'info',
                title: 'Terms Required',
                text: 'You must agree to the terms and conditions',
            });
            e.preventDefault();
            return;
        }

        // Show success message
        e.preventDefault(); 
        Swal.fire({
            icon: 'success',
            title: 'Success!',
            text: 'Admin or Employee has been successfully added',
        }).then(() => {
            this.submit();
        });
    });
});
