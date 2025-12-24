
// EVENT HANDLERS

// Function to show only the map and hide the image map and other sections


// Function to show only the map and hide other sections
function showSection(sectionId) {
    const sections = [
        'homepage', 'contactSection', 'wallsAthens', 'wallsIraklion',  'map',
        'wallsThessaloniki', 'aboutSection', 'uploadsPreview', 'eventsPreview', 'map-container'
    ];

    // Hide all sections except for the map
    sections.forEach(section => {
        const element = document.getElementById(section);
        if (element) {
            element.style.display = 'none';
        }
    });

    // Show the requested section (map)
    const targetSection = document.getElementById(sectionId);
    if (targetSection) {
        targetSection.style.display = 'block';
    }
    
    if (sectionId === 'map') {
        setTimeout(() => {
            map.invalidateSize();
        }, 300); // Delay ensures the map container is fully visible before recalculating
    }   
    
}


// Functionality of findBombsButton

document.getElementById('findBombsButton').addEventListener('click', () => {
    // Show homepage sections
    document.getElementById('homepage').style.display = 'block';
    document.getElementById('uploadsPreview').style.display = 'block';
    document.getElementById('eventsPreview').style.display = 'block';

    // Show map-container and its child elements
    const mapContainer = document.getElementById('map-container');
    if (mapContainer) {
        mapContainer.style.display = 'block';
    }

    const mapElement = document.getElementById('map');
    if (mapElement) {
        mapElement.style.display = 'none'; // Hide the Leaflet map
    }

    // Hide other sections
    const sectionsToHide = [
        'contactSection', 'wallsAthens', 'wallsIraklion', 'wallsThessaloniki', 'aboutSection'
    ];
    sectionsToHide.forEach(sectionId => {
        const section = document.getElementById(sectionId);
        if (section) {
            section.style.display = 'none';
        }
    });
});







// Menu dropdown functionality 
const menuBtn = document.getElementById('menuBtn');
const dropdownMenu = document.getElementById('dropdownMenu');

menuBtn.addEventListener('click', () => {
    dropdownMenu.style.display = dropdownMenu.style.display === 'block' ? 'none' : 'block';
});

// Close dropdown if clicking outside
window.onclick = function(event) {
    if (!event.target.matches('#menuBtn') && !event.target.closest('#dropdownMenu')) {
        dropdownMenu.style.display = 'none';
    }
};



// Menu button functionalities for sections
document.getElementById('myaccountLink').addEventListener('click', () => {
    showSection('contactSection');
    const username = prompt("Enter your username:");
    const password = prompt("Enter your password:");
    alert(`Login attempted for: ${username}`);
});

document.getElementById('contactLink').addEventListener('click', () => {
    showSection('contactSection');
});

document.getElementById('wallsAthensLink').addEventListener('click', () => {
    showSection('wallsAthens');
});

document.getElementById('wallsIrakleionLink').addEventListener('click', () => {
    showSection('wallsIraklion');
});

document.getElementById('wallsThessalonikiLink').addEventListener('click', () => {
    showSection('wallsThessaloniki');
});

document.getElementById('aboutLink').addEventListener('click', () => {
    showSection('aboutSection');
});





// Language change functionality
document.getElementById('btn3').addEventListener('click', () => {
    const language = prompt("Choose language (en/fr/es):");
    alert(`Language changed to: ${language}`);
});

// Search functionality
document.getElementById('searchInput').addEventListener('input', function() {
    const query = this.value.toLowerCase();
    console.log(`Searching for: ${query}`);
});






// Contact form validation
const contactForm = document.getElementById('contactForm');
contactForm.addEventListener('submit', function(event) {
    event.preventDefault();
    let valid = true;

    // Name validation
    const name = document.getElementById('name').value.trim();
    const nameError = document.getElementById('nameError');
    if (name === '') {
        valid = false;
        nameError.textContent = 'Name is required';
        nameError.style.display = 'block';
    } else {
        nameError.style.display = 'none';
    }

    // Email validation
    const email = document.getElementById('email').value.trim();
    const emailError = document.getElementById('mailError');
    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    if (!emailPattern.test(email)) {
        valid = false;
        emailError.textContent = 'Invalid email address';
        emailError.style.display = 'block';
    } else {
        emailError.style.display = 'none';
    }

    // Message validation
    const message = document.getElementById('message').value.trim();
    const messageError = document.getElementById('messageError');
    if (message === '') {
        valid = false;
        messageError.textContent = 'Message is required';
        messageError.style.display = 'block';
    } else {
        messageError.style.display = 'none';
    }

    // Display success or failure message
    if (valid) {
        document.getElementById('forMessage').textContent = 'Form submitted successfully!';
        contactForm.reset();
    } else {
        document.getElementById('forMessage').textContent = '';
    }
});





// "See More" buttons functionality for uploads and events
document.getElementById('btn1').addEventListener('click', () => {
    showSection('uploadsPreview');
    const uploadsPreview = document.getElementById('uploadsPreview');
    
    // Check if the 'More uploads' message already exists
    if (!uploadsPreview.querySelector('p.more-content')) {
        const newContent = document.createElement('p');
        newContent.textContent = 'More uploads coming soon!';
        newContent.classList.add('more-content'); // Add a class to easily target it
        uploadsPreview.appendChild(newContent);
    }
});

document.getElementById('btn2').addEventListener('click', () => {
    showSection('eventsPreview');
    const eventsPreview = document.getElementById('eventsPreview');
    
    // Check if the 'More events' message already exists
    if (!eventsPreview.querySelector('p.more-content')) {
        const newContent = document.createElement('p');
        newContent.textContent = 'More events coming soon!';
        newContent.classList.add('more-content'); // Add a class to easily target it
        eventsPreview.appendChild(newContent);
    }
});