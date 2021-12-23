/* eslint-disable react/jsx-props-no-spreading */
import React from "react";
import styled from "@emotion/styled";

const DropButton = styled.div`
	width: 95%;
	position: relative;
	display: inline-block;
	&:hover .dropbox {
		display: flex;
	}

	.dropbox {
		display: none;
		position: relative; // 주문하기 버튼이 보임 (absolute : 안보임)
		flex-direction: column;
		background: #ffffff;
		width: 95%;
		padding-bottom: 10px;

		&: nth-of-type(1) {
			padding-left: 30px;
		}
		div:nth-of-type(2) {
			padding-right: 30px;
		}
		& > li {
			display: flex;
			justify-content: space-between;
			margin: 13px;
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
`;

const PayInfo: React.FC = () => {
	return (
		<DropButton>
			<DropTitle>
				<div>결제 정보</div>
				<div>▼</div>
			</DropTitle>
			<ul className="dropbox">
				<li>
					<div>주문금액</div>
					<div>59,000원</div>
				</li>
				<li>
					<div>할인/부가결제</div>
					<div>-0원</div>
				</li>
				<li>
					<div>배송비</div>
					<div>2,500원</div>
				</li>
				<li>
					<div>총 결제금액</div>
					<div>61,500원</div>
				</li>
			</ul>
		</DropButton>
	);
};

export default PayInfo;
