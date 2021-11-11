import React, { useEffect, useState } from "react";
import axios from "axios";
import styled from "@emotion/styled";
import ShopCard from "../../molecules/ShopMenu/ShopCard";

const ShopList = styled.ul`
	width: 100%;
	height: 100%;
	display: flex;
	flex-wrap: wrap;
`;

const ShopListLi = styled.li`
	width: calc(20% - 20px);
	position: relative;
	margin-bottom: 40px;
	&:nth-of-type(5n) {
		margin-right: 0;
	}
	&:not(:nth-of-type(5n)) {
		margin-right: 25px;
	}
`;

const shopExam = [
	{
		id: 0,
		image: "http://www.kns.tv/news/photo/201808/462315_343258_317.jpg",
		shopName: "전주비빔밥",
		productName: "전통 전주비빔밥",
		shopFoodSale: 10,
		discountPrice: 9000,
		price: 10000,
		category: "한식",
	},
	{
		id: 1,
		image: "http://thingool.hgodo.com/gd5replace/thingotr4652/data/editor/goods/210617/285f14f889d5eb44a9e691f9f0ac985f_174805.jpg",
		shopName: "전통 부대찌개 맛집",
		productName: "맛있는 부대찌개",
		shopFoodSale: 5,
		discountPrice: 9500,
		price: 10000,
		category: "한식",
	},
	{
		id: 2,
		image: "https://www.hsd.co.kr/assets/images/main/main_img_01.jpg",
		shopName: "그냥도시락",
		productName: "그냥맛있는치킨마요덮밥",
		shopFoodSale: 10,
		discountPrice: 9000,
		price: 10000,
		category: "1인분",
	},
	{
		id: 3,
		image: "https://img1.daumcdn.net/thumb/R1280x0.fpng/?fname=http://t1.daumcdn.net/brunch/service/user/2AuX/image/B9-0Ny9Bc9a9VcaH3w22h2bubRQ.png",
		shopName: "그냥카페디저트",
		productName: "그냥맛있는케이크",
		shopFoodSale: 5,
		discountPrice: 9500,
		price: 10000,
		category: "카페디저트",
	},
	{
		id: 4,
		image: "https://lh3.googleusercontent.com/proxy/EAaKjOmk6wI8U__JS4jC2Ncr0_1jFB97BLxCByhKLr2qAYsG1owCNfGP9oAlJUbQRWs_wnR94Z5xeoU9LeJrPWNScXvStYzWKRsjJfW5l64cT-KisepgsGzPA6qTjRXkJT_YgHxgXWaCMNqF6YFf6Aq7i7b5pjlQ-IEj",
		shopName: "그냥치킨집",
		productName: "그냥맛있는치킨",
		shopFoodSale: 10,
		discountPrice: 9000,
		price: 10000,
		category: "치킨",
	},
	{
		id: 5,
		image: "https://img.hankyung.com/photo/202108/99.26501439.1-1200x.jpg",
		shopName: "그냥피자",
		productName: "그냥맛있는피자",
		shopFoodSale: 5,
		discountPrice: 9500,
		price: 10000,
		category: "피자양식",
	},
	{
		id: 6,
		image: "https://www.newiki.net/w/images/thumb/d/d9/Jjajangmyeon.jpg/1200px-Jjajangmyeon.jpg",
		shopName: "그냥중국집",
		productName: "그냥짜장면",
		shopFoodSale: 10,
		discountPrice: 9000,
		price: 10000,
		category: "중국집",
	},
	{
		id: 7,
		image: "http://mbcmall.imbc.com/store/__icsFiles/afieldfile/2018/12/27/001_1.jpg",
		shopName: "그냥돈까스",
		productName: "그냥맛있는돈까스",
		shopFoodSale: 5,
		discountPrice: 9500,
		price: 10000,
		category: "일식돈까스",
	},
	{
		id: 8,
		image: "https://mblogthumb-phinf.pstatic.net/20160908_177/dew36_1473339333121cJG9V_JPEG/1.jpg?type=w2",
		shopName: "그냥닭발집",
		productName: "그냥맛있는닭발",
		shopFoodSale: 10,
		discountPrice: 9000,
		price: 10000,
		category: "야식",
	},
	{
		id: 9,
		image: "http://th1.tmon.kr/thumbs/image/949/93e/82a/c7abc881a_700x700_95_FIT.jpg",
		shopName: "그냥족발집",
		productName: "그냥맛있는족발",
		shopFoodSale: 5,
		discountPrice: 9500,
		price: 10000,
		category: "족발보쌈",
	},
];

type shopsDataType = {
	id: number;
	image: string;
	shopName: string;
	productName: string;
	shopFoodSale: number;
	discountPrice: number;
	price: number;
	category: string;
	data?: Array<string | number>;
};

const SHOP_BOARD_API_BASE_URL = "http://localhost:8080/api/shopboard";
const BoardService = () => {
	return axios.get(SHOP_BOARD_API_BASE_URL);
};

const calculatedDiscount = (price: number, discountPrice: number): number => {
	return Math.ceil((1 - discountPrice / price) * 100);
};

interface menuMatchType {
	menuMatchName?: string;
}

const ShopCards: React.FC<menuMatchType> = ({ menuMatchName }) => {
	const [shop, setShops] = useState<shopsDataType[]>([]);
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
		<ShopList>
			{shop.map((row) =>
				row.category === menuMatchName ? (
					<ShopListLi key={row.id}>
						<ShopCard
							shopName={row.shopName}
							shopFoodImg={row.image}
							shopFoodName={row.productName}
							shopFoodSale={calculatedDiscount(
								row.price,
								row.discountPrice
							)}
							shopFoodCost={row.discountPrice}
							shopBeforeCost={row.price}
						/>
					</ShopListLi>
				) : null
			)}
		</ShopList>
	);
};

ShopCards.defaultProps = {
	menuMatchName: "한식",
};

export default ShopCards;
