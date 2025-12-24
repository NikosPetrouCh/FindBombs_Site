// 1) DEFAULT HOME PAGE SETUP


// Show home page by default and hide other sections
document.getElementById('homepage').style.display = 'block';
document.getElementById('uploadsPreview').style.display = 'block';
document.getElementById('eventsPreview').style.display = 'block';
document.getElementById('map-container').style.display = 'block';

document.querySelector('.logo-button').addEventListener('click', () => {
    // Sections to hide
    const sections = [
        'contactSection', 'wallsAthens', 'wallsIraklion', 'wallsThessaloniki',
        'aboutSection', 'map-container', 'map', 'mainImage', 'uploadsPreview',
        'eventsPreview'
    ];

    // Hide all sections
    sections.forEach(section => {
        const element = document.getElementById(section);
        if (element) {
            element.style.display = 'none';
        }
    });

    // Show homepage and relevant previews
    document.getElementById('homepage').style.display = 'block';
    document.getElementById('uploadsPreview').style.display = 'block';
    document.getElementById('eventsPreview').style.display = 'block';
});







