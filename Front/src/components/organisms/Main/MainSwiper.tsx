import React, { useEffect, useState } from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import SwiperCore, { Navigation, Pagination, Autoplay } from "swiper";
import { css } from "@emotion/react";
import axios from "axios";
import "swiper/css/bundle";
import "swiper/css";
import MainSwiperContent from "./MainSwiperContent";

SwiperCore.use([Navigation, Pagination, Autoplay]);

const changeColor = css`
	padding: 80px 0 30px 0;
	.swiper-pagination-bullet-active {
		background: #54b689;
	}

	.swiper-button-prev,
	.swiper-button-next {
		color: #54b689;
	}
`;

const textStyle = css`
	font-size: 40px;
	color: #333;
	font-weight: 700;
	display: flex;
	align-items: center;
	left: 0;
	position: absolute;

	p {
		color: red;
		margin-left: 10px;
		font-size: 30px;
		font-weight: 700;
	}
`;
const SHOP_BOARD_API_BASE_URL = "http://54.180.134.20/api/products/recommend";
const BoardService = () => {
	return axios.get(SHOP_BOARD_API_BASE_URL);
};
const MainSwiper: React.FC = () => {
	const [productCount, setProductCount] = useState<number>(0);
	useEffect(() => {
		BoardService().then((res) => {
			const shops = res.data.data;
			console.log(shops);
			const count = Math.ceil(shops.length / 4);
			setProductCount(count);
		});
	}, []);

	function forswiper(): React.ReactNode {
		const array1 = [];
		for (let i = 1; i <= productCount; i += 1) {
			array1.push(
				<SwiperSlide key={i}>
					<MainSwiperContent
						start={0 + (i - 1) * 4}
						last={4 + (i - 1) * 4}
					/>
				</SwiperSlide>
			);
		}
		return array1;
	}
	return (
		<>
			<h1 css={textStyle}>
				이거 어때?<p>Now</p>
			</h1>
			<Swiper
				css={changeColor}
				className="banner"
				spaceBetween={50}
				slidesPerView={1}
				navigation
				loop
				pagination={{ clickable: true }}
				autoplay={{ delay: 5000 }}
			>
				{forswiper()}
			</Swiper>
		</>
	);
};

export default MainSwiper;
