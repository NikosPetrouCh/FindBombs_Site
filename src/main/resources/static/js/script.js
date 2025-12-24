// DEFAULT HOME PAGE SETUP

document.addEventListener('DOMContentLoaded', function() {
    // Show home page by default and hide other sections
    const homepage = document.getElementById('homepage');
    const uploadsPreview = document.getElementById('uploadsPreview');
    const eventsPreview = document.getElementById('eventsPreview');
    const mapContainer = document.getElementById('map-container');

    if (homepage) homepage.style.display = 'block';
    if (uploadsPreview) uploadsPreview.style.display = 'block';
    if (eventsPreview) eventsPreview.style.display = 'block';
    if (mapContainer) mapContainer.style.display = 'block';

    const findBombsButton = document.getElementById('findBombsButton');
    if (findBombsButton) {
        findBombsButton.addEventListener('click', (e) => {
            e.preventDefault();
            // Navigation is handled by Thymeleaf links now
            window.location.href = '/';
        });
    }
});


