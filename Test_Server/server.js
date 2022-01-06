const express = require("express");
const app = express();
const port = 8888;

app.use(express.json());
app.get("/api/shops", (req, res) => {
  res.send("GET 된 상태입니다.");
});

app.post("/api/shops", (req, res) => {
  res.send("POST 된 상태입니다.");
});

app.get("/login", (req, res) => {
  res.send("업로드 된 상태입니다.");
});

app.post("/login", (req, res) => {
  const body = req.body;
  res.send({
    body: body,
  });
  res.send("로그인 버튼 눌렸습니다!");
});

app.listen(port, () => {
  console.log("서버 돌아가고 있습니다.");
});
