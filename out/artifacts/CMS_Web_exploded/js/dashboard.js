function validateForm(form) {
    const remarks = form.remarks.value.trim();
    if (remarks === "") {
        alert("Please enter remarks.");
        return false;
    }
    return true;
}
