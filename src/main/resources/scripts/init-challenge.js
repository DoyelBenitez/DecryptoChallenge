// Archivo: init-challenge.js
db.createUser({
    user: "mongo",
    pwd: "mongo",
    roles: [
        { role: "readWrite", db: "DECRYPTO" }
    ]
})

