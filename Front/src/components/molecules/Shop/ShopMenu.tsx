import React from "react";
import styled from "@emotion/styled";
import { css } from "@emotion/react";
import { Link } from "react-router-dom";

const ShopCardStyle = styled.div`
	border-bottom-left-radius: 1px;
	border-bottom-right-radius: 1px;
	box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.3);
	height: 100%;
	display: flex;
	flex-direction: row; // 세로방향
`;

const ShopImg = styled.img`
	width: 100%;
	height: 222px;
	object-fit: cover;
	transition: all 0.3s ease-in-out;
	transform: scale(1);
	&: hover {
		transform: scale(1.02);
		transition: all 0.3s ease-in-out;
	}
`;

const ShopCover = css`
	height: 222px;
	overflow: hidden;
	display: block;
	cursor: pointer;
`;

const ShopCardInfo = styled.div`
	margin: 15px 15px;
`;

const ShopCardInfoHead = styled.div`
	display: flex;
	flex-direction: column;
	align-items: flex-start;
`;

const shopNameStyle = css`
	color: #333;
	font-size: 12px;
	font-weight: 500;
`;

const shopFoodNameStyle = css`
	color: #333;
	font-size: 19px;
	font-weight: 700;
`;

type shopCardType = {
	shopName: string;
	shopFoodImg: string;
	shopFoodName: string;
};

const ShopCard: React.FC<shopCardType> = ({
	shopName,
	shopFoodImg,
	shopFoodName,
}) => {
	return (
		<ShopCardStyle>
			<Link to="/shop" css={ShopCover}>
				<ShopImg src={shopFoodImg} alt="음식점 사진" />
			</Link>
			<ShopCardInfo>
				<ShopCardInfoHead>
					<p css={shopNameStyle}>{shopName}</p>
					<p css={shopFoodNameStyle}>{shopFoodName}</p>
				</ShopCardInfoHead>
			</ShopCardInfo>
		</ShopCardStyle>
	);
};

export default ShopCard;
