
// SLIDERS
document.addEventListener("DOMContentLoaded", function () {

    // Current slide indices for each section
    let currentSlides = {
        wallsAthens: 0,
        wallsIraklion: 0,
        wallsThessaloniki: 0
    };

    // Function to show the current slide for a specific section
    function showSlide(images, index) {
        images.forEach((img, i) => {
            img.style.display = i === index ? "block" : "none"; // Show only the active slide
        });
    }

    // Function to dynamically load image data into a slider
    function loadSliderData(sliderKey, images) {
        const sliderContainer = document.querySelector(`#${sliderKey} .slider-container`);

        // Clear any existing images in the container
        sliderContainer.innerHTML = "";

        // Dynamically add images to the slider container
        images.forEach((image) => {
            const img = document.createElement("img");
            img.src = image.src;
            img.alt = image.alt;
            img.className = "slider-image"; // Add the correct class for slider images
            sliderContainer.appendChild(img);
        });

        // Select the dynamically added images for the slider
        const sliderImages = sliderContainer.querySelectorAll(".slider-image");

        // Initialize slider display (show the first image)
        showSlide(sliderImages, currentSlides[sliderKey]);

        // Attach event listeners for "Previous" and "Next" buttons
        document.getElementById(`prevBtn-${sliderKey}`).addEventListener("click", () => {
            currentSlides[sliderKey] = (currentSlides[sliderKey] > 0) ? currentSlides[sliderKey] - 1 : sliderImages.length - 1;
            showSlide(sliderImages, currentSlides[sliderKey]);
        });

        document.getElementById(`nextBtn-${sliderKey}`).addEventListener("click", () => {
            currentSlides[sliderKey] = (currentSlides[sliderKey] < sliderImages.length - 1) ? currentSlides[sliderKey] + 1 : 0;
            showSlide(sliderImages, currentSlides[sliderKey]);
        });
    }

    // Load the image data for each slider
    loadSliderData("wallsAthens", imagesData.wallsAthens);
    loadSliderData("wallsIraklion", imagesData.wallsIraklion);
    loadSliderData("wallsThessaloniki", imagesData.wallsThessaloniki);
});
