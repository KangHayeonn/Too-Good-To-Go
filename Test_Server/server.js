const express = require("express");
const cors = require("cors");
const app = express();
const port = 8888;

app.use(express.json()); // JSON 형식 처리하도록
app.use(cors()); // 모든 브라우저에서 내 서버에 요청 가능 -> cors 에러 방지

app.get("/api/shops", (req, res) => {
  // req: 요청(get 일 경우 ?query) , res : 응답
  const query = req.query;
  console.log(query);
  res.send({
    data: [
      {
        id: 0,
        shop: {
          id: 6651,
          name: "맛좋은 부침개",
          categories: ["분식"],
          phone: "010-5545-3287",
          image:
            "https://recipe1.ezmember.co.kr/cache/recipe/2015/08/06/bd3070e2cd18bb7caca0a64e768f72dd1.jpg",
        },
        products: [
          {
            id: 999,
            quantity: 3,
            name: "김치전",
            price: 18000,
          },
          {
            id: 997,
            quantity: 3,
            name: "부추전",
            price: 12000,
          },
        ],
        status: "CANCELED",
        createdAt: "2018-03-27T11:00:00+09:00",
        accept: "true",
        pickupAt: "2018-03-27T11:00:00+09:27",
        request: "한밤중에 목이말라 냉장고를 열어보니",
      },
      {
        id: 1,
        shop: {
          id: 2567,
          name: "맛있는 가게",
          categories: ["생선구이"],
          phone: "010-2222-3333",
          image:
            "https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202012/11/0622aaf6-e6c3-4abe-b165-08f1d5921e30.jpg",
        },
        products: [
          {
            id: 774,
            quantity: 5,
            name: "고등어 한마리",
            price: 9000,
          },
        ],
        status: "PICKUP_COMPLETED",
        createdAt: "2016-06-30T11:00:00+09:00",
        accept: "true",
        pickupAt: "2016-03-27T11:00:00+09:31",
        request: "한 귀퉁이에 고등어가 소금에 절여져 있네",
      },
    ],
  });
});

app.post("/api/shops", (req, res) => {
  // http에 요청을 할 때 header와 body가 있음 (body에 데이터를 담을 수 있음)
  // 브라우저 url창에는 get 요청만 가능 ( post 테스트 -> postman )
  const body = req.body;
  // key(body) 와 value(body) 가 완전히 똑같을 때는 body 하나로 생략 가능
  res.send({
    body,
  });
});

app.get("/login", (req, res) => {
  res.send("업로드 된 상태입니다.");
});

// response 두번 send 하면 에러 뜸 ([ERR_HTTP_HEADERS_SENT]: Cannot set headers after they are sent to the client)
app.post("/login", (req, res) => {
  const body = req.body;
  // key(body) 와 value(body) 가 완전히 똑같을 때는 body 하나로 생략 가능
  res.send({
    body: body,
  });
});

app.listen(port, () => {
  console.log("서버 돌아가고 있습니다.");
});
