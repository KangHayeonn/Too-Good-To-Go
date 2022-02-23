/* eslint-disable react/jsx-props-no-spreading */
import React, { useState } from "react";
import styled from "@emotion/styled";
import { useSelector } from "react-redux";
import { RootState } from "../../../app/store";

const DropButton = styled.div<{ displayType: boolean }>`
	width: 95%;
	position: relative;
	display: inline-block;
	margin-bottom: 3em;
	/*
	&:hover .dropbox {
		display: block; // block으로 바꾸면 hover 에도 visible 가능
	}
	*/
	padding-bottom: 5em;

	.dropbox {
		display: ${(props) => (props.displayType ? "none" : "block")};
		position: relative; // 주문하기 버튼이 보임 (absolute : 안보임)
		flex-direction: column;
		background: #ffffff;
		width: 1050px;
		padding-bottom: 10px;
		margin: 15px;
		margin-left: 3px;

		&: nth-of-type(1) {
			padding-left: 30px;
			.menu {
				margin-left: -10px;
				color: #000;
				width: 450px;
				display: flex;
			}
			.beforeCost {
				width: 390px;
				color: #949494;
				display: flex;
			}
			.cost {
				width: 165px;
				display: flex;
				justify-content: flex-start;
				color: #000;
			}
		}
		div:nth-of-type(2) {
			padding-right: 20px;
		}
		& > li {
			display: flex;
			margin: 27px 13px 27px 13px; // 위 오 아래 왼
			color: #646464;
			&: nth-of-type(1) {
				margin-top: 30px;
				margin-left: -15px;
				padding-left: 17px;
				padding-top: 25px;
				padding-bottom: 25px;
				border-bottom: 2px solid #eee;
				font-weight: bold;
				color: #262626;
				justify-content: space-between;
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

const OrderList: React.FC = () => {
	const [hidden, setHidden] = useState(true);
	const show = () => setHidden((current) => !current);
	const cartItem = useSelector((state: RootState) => {
		return state.selectCartCards;
	});

	return (
		<DropButton displayType={hidden}>
			<DropTitle>
				<div>주문내역</div>
				<button type="button" onClick={show}>
					{hidden ? "▼" : "▲"}
				</button>
			</DropTitle>
			<ul className="dropbox">
				<li className="title">
					<div>{cartItem[0].shopName}</div>
					<div>
						{cartItem[0].name} 외 {cartItem.length - 1}개
					</div>
				</li>
				{cartItem.map((e) => {
					return (
						<li key={e.id}>
							<div className="menu">
								{e.name} {e.cartItemQuantity}개
							</div>
							<div className="beforeCost">
								· 기본 : <s>{e.price}원</s>
							</div>
							<div className="cost">
								· 할인 후 가격 : {e.discountedPrice}원
							</div>
						</li>
					);
				})}
			</ul>
		</DropButton>
	);
};

export default OrderList;
