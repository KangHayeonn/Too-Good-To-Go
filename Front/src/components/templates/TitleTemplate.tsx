/* eslint-disable react/jsx-props-no-spreading */
import React, { useState } from "react";
import styled from "@emotion/styled";
import ShopEditModal from "../molecules/Shop/ShopEditModal";

const Wrapper = styled.div`
	display: flex;
	flex-direction: row; // 세로방향
	width: 100%;
	margin: 0 auto;
	align-items: center;
	justify-content: flex-start;
	box-shadow: 0px 1px 6px rgba(0, 0, 0, 0.2);
	padding: 1.7em;
	.select-btn {
		width: 74px;
		height: 27px;
		border-radius: 8px;
		background-color: #cfcfcf;
		color: black;
		font-weight: 500;
		font-size: 13px;
		margin-left: 1.7em;
	}
`;

const ShopImage = styled.img`
	width: 14%;
	height: 100%;
	background-color: green;
	display: flex;
	justify-content: center;
	align-items: center;
	margin-right: 1.7em;
`;

const ShopTitle = styled.div`
	width: 300px;
	height: 50px;
	font-size: 27px;
	font-weight: 600;
	display: flex;
	align-items: center;
`;

const ShopTime = styled.image`
	width: 200px;
	height: 30px;
	font-size: 15px;
	display: flex;
	align-items: flex-start;
	font-weight: 500;
	color: #b4b4b4;
`;

const Content = styled.section`
	width: 13.5%;
	//box-sizing: border-box; /* box사이즈를 기준으로 요소의 너비와 높이를 계산 */
	//margin: 1px auto;
	padding: 1.5em;
	display: flex;
	flex-direction: column;
`;

type Props = {
	image: string; // string 이나 이미지는 어떻게 넣지,,,
	title: string;
	time: string;
	isEdit: boolean;
};

const TitleTemplate: React.FC<Props> = ({
	image,
	title,
	time,
	isEdit,
	...props
}) => {
	const [isModal, setIsModal] = useState(false);
	const handleModal = () => {
		setIsModal(!isModal);
	};
	return (
		<Wrapper {...props}>
			<ShopImage src={image} alt="음식점 사진" />
			<Content>
				<ShopTitle>{title}</ShopTitle>
				<ShopTime>{time}</ShopTime>
			</Content>
			{isEdit ? (
				<>
					<button
						className="select-btn"
						type="button"
						onClick={handleModal}
					>
						수정
					</button>
					{!!isModal && (
						<ShopEditModal
							modal={handleModal}
							shopFoodName={title}
							shopBeforeCost={0}
							shopFoodCost={0}
						/>
					)}
				</>
			) : null}
		</Wrapper>
	);
};

export default TitleTemplate;
