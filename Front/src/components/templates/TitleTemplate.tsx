/* eslint-disable react/jsx-props-no-spreading */
import React from "react";
import styled from "@emotion/styled";

const Wrapper = styled.div`
	display: flex;
	flex-direction: row; // 세로방향
	width: 1115px;
	margin: 0 auto;
	display: flex;
	justify-content: center;
	box-shadow: 0px 1px 6px rgba(0, 0, 0, 0.2);
	padding: 1.7em;
`;

const ShopImage = styled.img`
	width: 165px;
	height: 170px;
	background-color: green;
	display: flex;
	justify-content: center;
	align-items: center;
	margin-right: 1.7em;
`;

const ShopTitle = styled.div`
	width: 300px;
	height: 40px;
	font-size: 29px;
	font-weight: 600;
	display: flex;
	align-items: flex-end;
	margin-top: -3px;
`;

const ShopAddress = styled.div`
	width: 300px;
	height: 30px;
	font-size: 27px;
	font-weight: 600;
	display: flex;
	align-items: flex-end;
	margin-top: 20px;
`;
const ItemCard = styled.div`
	width: 300px;
	height: 30px;
	font-size: 27px;
	font-weight: 600;
	display: flex;
	align-items: flex-end;
`;
const ItemsCard = styled.div`
	width: 300px;
	height: 30px;
	font-size: 27px;
	font-weight: 600;
	display: flex;
	align-items: flex-end;
`;

const ItemTitle = styled.div`
	width: 120px;
	height: 30px;
	font-size: 15px;
	display: flex;
	align-items: flex-end;
	font-weight: 500;
	color: #747474;
`;

const ItemContent = styled.div`
	width: 400px;
	height: 30px;
	font-size: 15px;
	display: flex;
	align-items: flex-end;
	font-weight: 500;
	color: #b4b4b4;
`;

const ItemTime = styled.div`
	width: 100px;
	height: 30px;
	font-size: 15px;
	display: flex;
	align-items: flex-end;
	font-weight: 500;
	color: #b4b4b4;
	margin-left: -50px;
	margin-right: 30px;
`;

const Content = styled.section`
	width: 80%;
	//box-sizing: border-box; /* box사이즈를 기준으로 요소의 너비와 높이를 계산 */
	margin: 1px auto;
	padding: 1.5em;
	display: flex;
	flex-direction: column;
`;

type Props = {
	image: string; // string 이나 이미지는 어떻게 넣지,,,
	title: string;
	time: string;
	address: string;
	phone: string;
};

const TitleTemplate: React.FC<Props> = ({
	image,
	title,
	time,
	address,
	phone,
	...props
}) => {
	return (
		<Wrapper {...props}>
			<ShopImage src={image} alt="ShopImage" />
			<Content>
				<ShopTitle>{title}</ShopTitle>
				<ShopAddress>
					<ItemTitle>주소</ItemTitle>
					<ItemContent>{address}</ItemContent>
				</ShopAddress>
				<ItemsCard>
					<ItemCard>
						<ItemTitle>영업시간</ItemTitle>
						<ItemTime>{time}</ItemTime>
					</ItemCard>
					<ItemCard>
						<ItemTitle>전화번호</ItemTitle>
						<ItemContent>{phone}</ItemContent>
					</ItemCard>
				</ItemsCard>
			</Content>
		</Wrapper>
	);
};

export default TitleTemplate;
