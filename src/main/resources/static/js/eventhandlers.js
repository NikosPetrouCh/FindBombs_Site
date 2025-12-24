// EVENT HANDLERS

// Menu dropdown functionality 
document.addEventListener('DOMContentLoaded', function() {
    const menuBtn = document.getElementById('menuBtn');
    const dropdownMenu = document.getElementById('dropdownMenu');
    
    if (menuBtn && dropdownMenu) {
        menuBtn.addEventListener('click', () => {
            dropdownMenu.style.display = dropdownMenu.style.display === 'block' ? 'none' : 'block';
        });
    }

    // Close dropdown if clicking outside
    window.onclick = function(event) {
        if (menuBtn && dropdownMenu && !event.target.matches('#menuBtn') && !event.target.closest('#dropdownMenu')) {
            dropdownMenu.style.display = 'none';
        }
    };

    // Language change functionality
    const btn3 = document.getElementById('btn3');
    if (btn3) {
        btn3.addEventListener('click', () => {
            const language = prompt("Choose language (en/fr/es):");
            alert(`Language changed to: ${language}`);
        });
    }

    // "See More" buttons functionality for uploads and events with smooth animation
    const btn1 = document.getElementById('btn1');
    if (btn1) {
        btn1.addEventListener('click', () => {
            const uploadsPreview = document.getElementById('uploadsPreview');
            const toggleBtn = btn1;
            if (uploadsPreview) {
                if (uploadsPreview.style.display === 'none' || !uploadsPreview.style.display) {
                    uploadsPreview.style.display = 'block';
                    uploadsPreview.style.opacity = '0';
                    uploadsPreview.style.transform = 'translateY(-10px)';
                    setTimeout(() => {
                        uploadsPreview.style.transition = 'all 0.3s ease';
                        uploadsPreview.style.opacity = '1';
                        uploadsPreview.style.transform = 'translateY(0)';
                    }, 10);
                    toggleBtn.textContent = '▲';
                } else {
                    uploadsPreview.style.transition = 'all 0.3s ease';
                    uploadsPreview.style.opacity = '0';
                    uploadsPreview.style.transform = 'translateY(-10px)';
                    setTimeout(() => {
                        uploadsPreview.style.display = 'none';
                    }, 300);
                    toggleBtn.textContent = '▼';
                }
            }
        });
    }

    const btn2 = document.getElementById('btn2');
    if (btn2) {
        btn2.addEventListener('click', () => {
            const eventsPreview = document.getElementById('eventsPreview');
            const toggleBtn = btn2;
            if (eventsPreview) {
                if (eventsPreview.style.display === 'none' || !eventsPreview.style.display) {
                    eventsPreview.style.display = 'block';
                    eventsPreview.style.opacity = '0';
                    eventsPreview.style.transform = 'translateY(-10px)';
                    setTimeout(() => {
                        eventsPreview.style.transition = 'all 0.3s ease';
                        eventsPreview.style.opacity = '1';
                        eventsPreview.style.transform = 'translateY(0)';
                    }, 10);
                    toggleBtn.textContent = '▲';
                } else {
                    eventsPreview.style.transition = 'all 0.3s ease';
                    eventsPreview.style.opacity = '0';
                    eventsPreview.style.transform = 'translateY(-10px)';
                    setTimeout(() => {
                        eventsPreview.style.display = 'none';
                    }, 300);
                    toggleBtn.textContent = '▼';
                }
            }
        });
    }

    // Add smooth scroll behavior
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    });

    // Add fade-in animation on page load
    window.addEventListener('load', () => {
        const mainContent = document.querySelector('.main-content');
        if (mainContent) {
            mainContent.style.opacity = '0';
            mainContent.style.transform = 'translateY(20px)';
            setTimeout(() => {
                mainContent.style.transition = 'all 0.6s ease';
                mainContent.style.opacity = '1';
                mainContent.style.transform = 'translateY(0)';
            }, 100);
        }
    });

    // Contact form validation
    const contactForm = document.getElementById('contactForm');
    if (contactForm) {
        contactForm.addEventListener('submit', function(event) {
            event.preventDefault();
            let valid = true;

            // Name validation
            const name = document.getElementById('name').value.trim();
            const nameError = document.getElementById('nameError');
            if (name === '') {
                valid = false;
                if (nameError) {
                    nameError.textContent = 'Name is required';
                    nameError.style.display = 'block';
                }
            } else {
                if (nameError) nameError.style.display = 'none';
            }

            // Email validation
            const email = document.getElementById('email').value.trim();
            const emailError = document.getElementById('mailError');
            const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
            if (!emailPattern.test(email)) {
                valid = false;
                if (emailError) {
                    emailError.textContent = 'Invalid email address';
                    emailError.style.display = 'block';
                }
            } else {
                if (emailError) emailError.style.display = 'none';
            }

            // Message validation
            const message = document.getElementById('message').value.trim();
            const messageError = document.getElementById('messageError');
            if (message === '') {
                valid = false;
                if (messageError) {
                    messageError.textContent = 'Message is required';
                    messageError.style.display = 'block';
                }
            } else {
                if (messageError) messageError.style.display = 'none';
            }

            // Display success or failure message
            if (valid) {
                const forMessage = document.getElementById('forMessage');
                if (forMessage) {
                    forMessage.textContent = 'Form submitted successfully!';
                }
                contactForm.reset();
            } else {
                const forMessage = document.getElementById('forMessage');
                if (forMessage) {
                    forMessage.textContent = '';
                }
            }
        });
    }
});

