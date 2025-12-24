
// Image data for each slider section
const imagesData = {
    wallsAthens: [
        { src: "assets/Aimages/Aimage1.jpg", alt: "Athens Image 1" },
        { src: "assets/Aimages/Aimage2.jpg", alt: "Athens Image 2" },
        { src: "assets/Aimages/Aimage3.jpg", alt: "Athens Image 3" }
    ],
    wallsIraklion: [
        { src: "assets/Iimages/Iimage1.jpg", alt: "Iraklion Image 1" },
        { src: "assets/Iimages/Iimage2.jpg", alt: "Iraklion Image 2" },
        { src: "assets/Iimages/Iimage3.jpg", alt: "Iraklion Image 3" }
    ],
    wallsThessaloniki: [
        { src: "assets/Timages/Timage1.jpg", alt: "Thessaloniki Image 1" },
        { src: "assets/Timages/Timage2.jpg", alt: "Thessaloniki Image 2" },
        { src: "assets/Timages/Timage3.jpg", alt: "Thessaloniki Image 3" }
    ]
};


// // Image data for each slider section
// const imageData = [
//     {
//         "name": "Aimage1",
//         "src": "assets/Aimages/Aimage1.jpg",
//         "alt": "Athens Image 1",
//         "city": "Athens",
//         "style": "wildstyle",
//         "dateOfUpload": "2023-12-01",
//         "streetAndAddress": "Ermou Street"
//     },
//     {
//         "name": "Aimage2",
//         "src": "assets/Aimages/Aimage2.jpg",
//         "alt": "Athens Image 2",
//         "city": "Athens",
//         "style": "tag",
//         "dateOfUpload": "2023-12-02",
//         "streetAndAddress": "Kolonaki Square"
//     }
// ]


// [
//     {
//         "name": "Iimage1",
//         "src": "assets/Iimages/Iimage1.jpg",
//         "alt": "Iraklion Image 1",
//         "city": "Iraklion",
//         "style": "bomb",
//         "dateOfUpload": "2023-11-28",
//         "streetAndAddress": "Knossos Avenue"
//     }
// ]


// [
//     {
//         "name": "Timage1",
//         "src": "assets/Timages/Timage1.jpg",
//         "alt": "Thessaloniki Image 1",
//         "city": "Thessaloniki",
//         "style": "wildstyle",
//         "dateOfUpload": "2023-11-20",
//         "streetAndAddress": "Aristotelous Square"
//     }
// ]


// function getImagesByCity(city) {
//     return imageData.filter(item => item.city === city);
// }

// const athensImages = getImagesByCity("Athens");
// console.log(athensImages);


// function getImagesByStyle(style) {
//     return imageData.filter(item => item.style === style);
// }

// const wildstyleImages = getImagesByStyle("wildstyle");
// console.log(wildstyleImages);


// function getAllDescriptions() {
//     return imageData.map(item => ({
//         description: item.description,
//         city: item.city,
//         style: item.style
//     }));
// }

// console.log(getAllDescriptions());


// function chooseRandomImageSet() {
//     return imageData[Math.floor(Math.random() * imageData.length)];
// }

// const randomSet = chooseRandomImageSet();
// console.log(randomSet);


// document.addEventListener("DOMContentLoaded", function () {
//     const gallery = document.getElementById("gallery"); // Assume you have a `gallery` div

//     // Populate gallery with Athens images
//     const athensImages = getImagesByCity("Athens");

//     athensImages.forEach(section => {
//         section.images.forEach(image => {
//             const imgElement = document.createElement("img");
//             imgElement.src = image.src;
//             imgElement.alt = image.alt;
//             gallery.appendChild(imgElement);
//         });
//     });
// });


// function validateImageData(data) {
//     const requiredKeys = ["description", "filename", "city", "style", "dateOfUpload", "streetAndAddress", "images"];
//     data.forEach(item => {
//         requiredKeys.forEach(key => {
//             if (!item[key]) {
//                 console.error(`Missing required key: ${key} in`, item);
//             }
//         });

//         if (!item.streetAndAddress) {
//             console.error(`Privacy error: Street and address is empty in`, item);
//         }

//         if (!["Athens", "Iraklion", "Thessaloniki"].includes(item.city)) {
//             console.error(`Invalid city: ${item.city} in`, item);
//         }

//         if (!["bomb", "tag", "wildstyle"].includes(item.style)) {
//             console.error(`Invalid style: ${item.style} in`, item);
//         }
//     });
// }

// validateImageData(imageData);


// class ImageManager {
//     constructor(jsonFiles) {
//         // JSON files for each city
//         this.jsonFiles = jsonFiles; // { Athens: 'wallsAthens.json', Iraklion: 'wallsIraklion.json', Thessaloniki: 'wallsThessaloniki.json' }
//         this.imageData = {}; // Cache loaded data
//     }

//     // Load a JSON file
//     async loadJson(file) {
//         const response = await fetch(file);
//         const data = await response.json();
//         this.imageData[file] = data; // Cache data
//         return data;
//     }

//     // Load all JSON files
//     async loadAllJsonFiles() {
//         const promises = Object.values(this.jsonFiles).map(file => this.loadJson(file));
//         await Promise.all(promises);
//     }

//     // Add a new image to the appropriate city file
//     async addImage(image) {
//         const city = image.city;
//         const file = this.jsonFiles[city];
//         if (!file) {
//             throw new Error(`Invalid city: ${city}`);
//         }

//         // Load data if not already cached
//         if (!this.imageData[file]) {
//             await this.loadJson(file);
//         }

//         // Add the image to the data
//         this.imageData[file].push(image);

//         // Save back to JSON (for now, simulate with console log)
//         console.log(`Updated ${file}:`, this.imageData[file]);
//         // Use a backend API or write logic to save this data to disk.
//     }

//     // Get images for a specific city
//     getImagesByCity(city) {
//         const file = this.jsonFiles[city];
//         return this.imageData[file] || [];
//     }

//     // Get images by style
//     getImagesByStyle(style) {
//         const allImages = Object.values(this.imageData).flat();
//         return allImages.filter(image => image.style === style);
//     }

//     // Choose a random image
//     chooseRandomImage() {
//         const allImages = Object.values(this.imageData).flat();
//         return allImages[Math.floor(Math.random() * allImages.length)];
//     }
// }


// const jsonFiles = {
//     Athens: 'wallsAthens.json',
//     Iraklion: 'wallsIraklion.json',
//     Thessaloniki: 'wallsThessaloniki.json'
// };

// const manager = new ImageManager(jsonFiles);

// (async () => {
//     await manager.loadAllJsonFiles();

//     // Get images by city
//     console.log("Athens Images:", manager.getImagesByCity("Athens"));

//     // Add a new image
//     await manager.addImage({
//         name: "Aimage3",
//         src: "assets/Aimages/Aimage3.jpg",
//         alt: "Athens Image 3",
//         city: "Athens",
//         style: "bomb",
//         dateOfUpload: "2023-12-03",
//         streetAndAddress: "Syntagma Square"
//     });

//     // Get images by style
//     console.log("Wildstyle Images:", manager.getImagesByStyle("wildstyle"));

//     // Choose a random image
//     console.log("Random Image:", manager.chooseRandomImage());
// })();
