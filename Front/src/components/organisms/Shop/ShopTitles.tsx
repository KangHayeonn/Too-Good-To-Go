import React, { useState, useEffect } from "react";
import axios from "axios";
import TitleTemplate from "../../templates/TitleTemplate";

const shopExam = [
	{
		id: 0,
		image: "http://www.kns.tv/news/photo/201808/462315_343258_317.jpg",
		shop: {
			id: 0,
			name: "전주비빔밥",
			image: "http://www.kns.tv/news/photo/201808/462315_343258_317.jpg",
			category: ["한식"],
		},
		name: "전통 전주비빔밥",
		discountedPrice: 9000,
		price: 10000,
	},
	{
		id: 1,
		image: "http://thingool.hgodo.com/gd5replace/thingotr4652/data/editor/goods/210617/285f14f889d5eb44a9e691f9f0ac985f_174805.jpg",
		shop: {
			id: 1,
			name: "전통 부대찌개 맛집",
			image: "http://thingool.hgodo.com/gd5replace/thingotr4652/data/editor/goods/210617/285f14f889d5eb44a9e691f9f0ac985f_174805.jpg",
			category: ["한식"],
		},
		name: "맛있는 부대찌개",
		discountedPrice: 9500,
		price: 10000,
	},
	{
		id: 2,
		image: "https://www.hsd.co.kr/assets/images/main/main_img_01.jpg",
		shop: {
			id: 2,
			name: "그냥도시락",
			image: "https://www.hsd.co.kr/assets/images/main/main_img_01.jpg",
			category: ["한식"],
		},
		name: "그냥맛있는치킨마요덮밥",
		shopFoodSale: 10,
		discountedPrice: 9000,
		price: 10000,
	},
	{
		id: 3,
		image: "https://img1.daumcdn.net/thumb/R1280x0.fpng/?fname=http://t1.daumcdn.net/brunch/service/user/2AuX/image/B9-0Ny9Bc9a9VcaH3w22h2bubRQ.png",
		shop: {
			id: 3,
			name: "그냥카페디저트",
			image: "https://img1.daumcdn.net/thumb/R1280x0.fpng/?fname=http://t1.daumcdn.net/brunch/service/user/2AuX/image/B9-0Ny9Bc9a9VcaH3w22h2bubRQ.png",
			category: ["디저트"],
		},
		name: "그냥맛있는케이크",
		discountedPrice: 9500,
		price: 10000,
	},
	{
		id: 4,
		image: "https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20211005_84%2F1633427100547JYF19_PNG%2F1u1r0WjPhZou6UMzRjVqb6us.png&type=a340",
		shop: {
			id: 4,
			name: "그냥치킨집",
			image: "https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20211005_84%2F1633427100547JYF19_PNG%2F1u1r0WjPhZou6UMzRjVqb6us.png&type=a340",
			category: ["치킨", "패스트푸드", "야식"],
		},
		name: "그냥맛있는치킨",
		shopFoodSale: 10,
		discountedPrice: 9000,
		price: 10000,
	},
	{
		id: 5,
		image: "https://img.hankyung.com/photo/202108/99.26501439.1-1200x.jpg",
		shop: {
			id: 5,
			name: "그냥피자",
			image: "https://img.hankyung.com/photo/202108/99.26501439.1-1200x.jpg",
			category: ["패스트푸드", "피자"],
		},
		name: "그냥맛있는피자",
		discountedPrice: 9500,
		price: 10000,
	},
	{
		id: 6,
		image: "https://www.newiki.net/w/images/thumb/d/d9/Jjajangmyeon.jpg/1200px-Jjajangmyeon.jpg",
		shop: {
			id: 6,
			name: "그냥중국집",
			image: "https://www.newiki.net/w/images/thumb/d/d9/Jjajangmyeon.jpg/1200px-Jjajangmyeon.jpg",
			category: ["중식"],
		},
		name: "그냥짜장면",
		shopFoodSale: 10,
		discountedPrice: 9000,
		price: 10000,
	},
	{
		id: 7,
		image: "http://mbcmall.imbc.com/store/__icsFiles/afieldfile/2018/12/27/001_1.jpg",
		shop: {
			id: 7,
			name: "그냥돈까스",
			image: "http://mbcmall.imbc.com/store/__icsFiles/afieldfile/2018/12/27/001_1.jpg",
			category: ["일식"],
		},
		name: "그냥맛있는돈까스",
		discountedPrice: 9500,
		price: 10000,
	},
	{
		id: 8,
		image: "https://mblogthumb-phinf.pstatic.net/20160908_177/dew36_1473339333121cJG9V_JPEG/1.jpg?type=w2",
		shop: {
			id: 8,
			name: "그냥닭발집",
			image: "https://mblogthumb-phinf.pstatic.net/20160908_177/dew36_1473339333121cJG9V_JPEG/1.jpg?type=w2",
			category: ["야식"],
		},
		name: "그냥맛있는닭발",
		shopFoodSale: 10,
		discountedPrice: 9000,
		price: 10000,
	},
	{
		id: 9,
		image: "http://th1.tmon.kr/thumbs/image/949/93e/82a/c7abc881a_700x700_95_FIT.jpg",
		shop: {
			id: 9,
			name: "그냥족발집",
			image: "http://th1.tmon.kr/thumbs/image/949/93e/82a/c7abc881a_700x700_95_FIT.jpg",
			category: ["야식"],
		},
		name: "그냥맛있는족발",
		discountedPrice: 9500,
		price: 10000,
	},
];

type shopsDataType = {
	id: number;
	image: string;
	name: string;
	discountedPrice: number;
	price: number;
	shop: {
		id: number;
		name: string;
		image: string;
		category: Array<string>;
	};
	data?: Array<string | number>;
};

// 상품 조회
// const PRODUCT_BASE_URL = "http://54.180.134.20/api/shop";
// 가게 조회
const SHOP_BASE_URL = "http://54.180.134.20/api/shops";

interface shopMatchId {
	shopMatchId?: number;
}
const ShopTitles: React.FC<shopMatchId> = ({ shopMatchId }) => {
	const [shop, setShops] = useState<shopsDataType[]>([]);
	const BoardService = () => {
		// 상품 조회
		// return axios.get(`${PRODUCT_BASE_URL}/${9}/products`);
		// 가게 조회
		return axios.get(`${SHOP_BASE_URL}`);
	};
	console.log(shopMatchId);
	useEffect(() => {
		BoardService().then(
			(res) => {
				setShops(res.data.data); // api가 연결 된 경우 -> back에서 데이터 불러옴
			},
			() => {
				setShops(shopExam); // api가 연결되지 않은 경우 -> 위의 예시 데이터 출력
			}
		);
	}, []);

	return (
		<div>
			{shop.map((row) => {
				return row.id === 9 ? (
					<TitleTemplate
						key={row.id}
						image={row.image}
						title={row.name}
						time="영업시간 10:00 ~ 22:00"
					/>
				) : null;
			})}
		</div>
	);
};

ShopTitles.defaultProps = {
	shopMatchId: 0,
};

export default ShopTitles;
