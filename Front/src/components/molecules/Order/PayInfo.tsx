/* eslint-disable react/jsx-props-no-spreading */
import React, { useEffect, useState } from "react";
import styled from "@emotion/styled";
import { useSelector } from "react-redux";
import { RootState } from "../../../app/store";

const DropButton = styled.div<{ displayType: boolean }>`
	width: 95%;
	position: relative;
	display: inline-block;

	.dropbox {
		display: ${(props) => (props.displayType ? "none" : "block")};
		position: relative; // 주문하기 버튼이 보임 (absolute : 안보임)
		flex-direction: column;
		background: #ffffff;
		width: 95%;
		padding-bottom: 10px;
		margin: 15px;
		margin-left: 9px;
		border-bottom: 2px solid #eee;

		&: nth-of-type(1) {
			padding-left: 17px;
		}
		div:nth-of-type(2) {
			padding-right: 30px;
		}
		& > li {
			display: flex;
			justify-content: space-between;
			margin: 27px 13px 27px 19px; // 위 오 아래 왼
			color: #646464;

			&: nth-of-type(4) {
				margin-top: 30px;
				margin-left: -10px;
				padding-left: 17px;
				padding-top: 25px;
				border-top: 2px solid #eee;
				font-weight: bold;
				color: #262626;
			}
		}
	}
`;

const DropTitle = styled.div`
	display: flex;
	justify-content: space-between;
	width: 95%;
	margin: 2em 1em 29px 1em;
	border-bottom: 1px solid #eee;
	padding-bottom: 1em;
	font-weight: bold;
	color: #4f4f4f;

	div:first-of-type {
		padding-left: 20px;
	}
	div:nth-of-type(2) {
		padding-right: 30px;
	}
	button {
		color: #4f4f4f;
		background: #ffffff;
		font-weight: bold;
		font-size: 16px;
		padding-right: 1.7em;
	}
`;

const Total = styled.div`
	width: 92%;
	height: 37px;
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-top: 17px;
	margin-left: 12px;
	padding-left: 23px;
	padding-right: 21px;
	background: #efefef;
	font-weight: bold;
	color: #646464;
	margin-bottom: 1em;
	&: nth-of-type(2) {
		padding-left: 17px;
		padding-right: 25px;
	}
`;

const PayInfo: React.FC = () => {
	const [price, setPrice] = useState<number>(0);
	const [discountPrice, setdiscountPrice] = useState<number>(0);
	const [deliveryFee, setDeliveryFee] = useState<number>(0);
	const [hidden, setHidden] = useState(true);

	const cartItem = useSelector((state: RootState) => {
		return state.selectCartCards;
	})

	useEffect(() => {
		let priceTotal = 0;
		let discountPriceTotal = 0; 
		cartItem.map((card) => {
			priceTotal += card.price*card.cartItemQuantity;
			discountPriceTotal += (card.price-card.discountedPrice)*card.cartItemQuantity;
			return 0;
		});
		setPrice(priceTotal);
		setdiscountPrice(discountPriceTotal);
		setDeliveryFee(2500);
	}, []);

	const show = () => {
		setHidden((current) => !current);
	};

	return (
		<DropButton displayType={hidden}>
			<DropTitle>
				<div>결제 정보</div>
				<button type="button" onClick={show}>
					{hidden ? "▼" : "▲"}
				</button>
			</DropTitle>
			<ul className="dropbox">
				<li>
					<div>주문금액</div>
					<div>{price}원</div>
				</li>
				<li>
					<div>할인/부가결제</div>
					<div>-{discountPrice}원</div>
				</li>
				<li>
					<div>배송비</div>
					<div>{deliveryFee}원</div>
				</li>
			</ul>
			<Total>
				<div>총 결제금액</div>
				<div>{price-discountPrice+deliveryFee}원</div>
			</Total>
		</DropButton>
	);
};

export default PayInfo;
