import React, { useState } from "react";
import styled from "@emotion/styled";

const DropPoint = styled.div<{ display: boolean }>`
	width: 95%;
	position: relative;
	display: inline-block;
	margin-bottom: -2em;

	.point {
		display: ${(props) => (props.display ? "none" : "block")};
		position: relative;
		flex-direction: column;
		background: #ffffff;
		width: 95%;
		padding-bottom: 10px;
		margin: 15px;
		margin-left: 3px;
	}
`;

const DropCoupon = styled.div<{ display2: boolean }>`
	width: 95%;
	position: relative;
	display: inline-block;
	margin-bottom: 3em;

	.coupon {
		display: ${(props) => (props.display2 ? "none" : "block")};
		position: relative;
		flex-direction: column;
		background: #ffffff;
		width: 95%;
		padding-bottom: 10px;
		margin: 15px;
		margin-left: 3px;
	}
`;

const DropTitle = styled.div`
	display: flex;
	justify-content: space-between;
	width: 95%;
	margin: 2em 1em 29px 1em;
	border-bottom: 2px solid #eee;
	padding-bottom: 1.5em;
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
		display: flex;
		&:hover {
			color: #387358;
		}
	}
`;

const DiscountInfo: React.FC = () => {
	const [hidden, setHidden] = useState(true);
	const [hidden2, setHidden2] = useState(true);
	const show = () => setHidden((current) => !current);
	const show2 = () => setHidden2((current2) => !current2);
	console.log(hidden);
	return (
		<div>
			<DropPoint display={hidden}>
				<DropTitle>
					<div>포인트</div>
					<button type="button" onClick={show}>
						1개 보유 {hidden ? "▶" : "◀"}
					</button>
				</DropTitle>
				<ul className="point">modal 출력</ul>
			</DropPoint>
			<DropCoupon display2={hidden2}>
				<DropTitle>
					<div>할인쿠폰</div>
					<button type="button" onClick={show2}>
						1개 보유 {hidden2 ? "▶" : "◀"}
					</button>
				</DropTitle>
				<ul className="coupon">modal 출력</ul>
			</DropCoupon>
		</div>
	);
};

export default DiscountInfo;
