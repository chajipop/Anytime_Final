function downloadImage(src) {
    fetch(src)
        .then(response => response.blob())
        .then(blob => {
            const url = URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = src.split('/').pop();
            document.body.appendChild(a);
            a.click();
            a.remove();
        })
    .catch(console.error);
}
