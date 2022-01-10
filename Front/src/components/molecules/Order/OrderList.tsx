/* eslint-disable react/jsx-props-no-spreading */
import React, { useState } from "react";
import styled from "@emotion/styled";

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
		width: 95%;
		padding-bottom: 10px;
		margin: 15px;
		margin-left: 3px;

		&: nth-of-type(1) {
			padding-left: 30px;
		}
		div:nth-of-type(2) {
			padding-right: 30px;
		}
		& > li {
			display: flex;
			justify-content: space-between;
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

	return (
		<DropButton displayType={hidden}>
			<DropTitle>
				<div>주문내역</div>
				<button type="button" onClick={show}>
					{hidden ? "▼" : "▲"}
				</button>
			</DropTitle>
			<ul className="dropbox">
				<li>
					<div>음식점 로고</div>
					<div>음식점 이름</div>
					<div>교촌허니콤보 외 3개</div>
				</li>
				<li>
					<div>교촌허니콤보 1개</div>
					<div>21,000원</div>
					<div>· 기본 : 20,000원</div>
					<div>· 소스 및 무우 추가선택 : 레드디핑소스(1,000원)</div>
				</li>
				<li>
					<div>교촌레드콤보[S] 1개</div>
					<div>11,000원</div>
					<div>· 기본 : 11,000원</div>
				</li>
				<li>
					<div>교촌무우 1개</div>
					<div>500원</div>
					<div>· 기본 : 500원</div>
				</li>
				<li>
					<div>펩시(사이즈업) 1개</div>
					<div>2,500원</div>
					<div>· 기본 : 1.25L (2,500원)</div>
				</li>
			</ul>
		</DropButton>
	);
};

export default OrderList;
