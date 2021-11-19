import React, { useEffect, useState } from "react";
import axios from "axios";
import styled from "@emotion/styled";

const Style = styled.td`
	border: 1px solid #999;
`;

const DivSt = styled.div`
	display: flex;
	width: 100%;
	flex-direction: column;
	align-items: center;
`;

const SHOP_BOARD_API_BASE_URL = "http://localhost:8080/api/shops";

type shopsDataType = {
	id: number;
	image: string;
	name: string;
	category: Array<string>;
	data?: Array<string | number>;
};

const ShopGet = () => {
	return axios.get(SHOP_BOARD_API_BASE_URL);
};

const PostTest: React.FC = () => {
	const [shopView, setShopView] = useState<shopsDataType[]>([]);
	const [shopInfo, setShopInfo] = useState({
		name: "",
		image: "",
		category: [""],
	});

	const [shopId, setShopId] = useState();

	useEffect(() => {
		ShopGet().then((res) => {
			setShopView(res.data.data);
		});
	});

	const getValue = (e: any) => {
		const { name, value } = e.target;
		setShopInfo({
			...shopInfo,
			[name]: value,
		});
		console.log(shopInfo);
	};

	const BoardService = () => {
		return axios.post(SHOP_BOARD_API_BASE_URL, shopInfo);
	};

	const post = () => {
		BoardService().then(
			() => {
				console.log("성공");
			},
			() => {
				console.log("실패 ㅠ");
			}
		);
	};

	const [checkedInput, setCheckedInput] = useState<Array<string>>([]);

	const chngeHandler = (checked: boolean, value: string) => {
		if (checked) {
			setCheckedInput([...checkedInput, value]);
		} else {
			setCheckedInput(checkedInput.filter((el) => el !== value));
		}
	};

	useEffect(() => {
		console.log(checkedInput);
		setShopInfo({
			...shopInfo,
			category: checkedInput,
		});
	}, [checkedInput]);

	useEffect(() => {
		console.log(shopInfo);
	}, [shopInfo]);

	const changeId = (e: any) => {
		const { value } = e.target;
		setShopId(value);
	};
	const PRODUCT_API_URL = `http://localhost:8080/api/shops/${shopId}/products`;
	const [productInfo, setProductInfo] = useState({
		name: "",
		price: Number,
		discountedPrice: Number,
		image: "",
	});

	const productPost = (e: any) => {
		const { name, value } = e.target;
		setProductInfo({
			...productInfo,
			[name]: value,
		});
		console.log(productInfo);
	};

	const BoardService2 = () => {
		return axios.post(PRODUCT_API_URL, productInfo);
	};

	const post2 = () => {
		BoardService2().then(
			() => {
				console.log("성공");
			},
			() => {
				console.log("실패 ㅠ");
			}
		);
	};

	return (
		<DivSt>
			<table>
				<th>가게아이디</th>
				<th>가게이름</th>
				<th>가게이미지(string)</th>
				<th>카데고리(Array)</th>
				{shopView.map((row) => (
					<tr>
						<Style>{row.id}</Style>
						<Style>{row.name}</Style>
						<Style>{row.image}</Style>
						<Style>{row.category}</Style>
					</tr>
				))}
			</table>

			<h1>-가게정보-</h1>
			<h4>가게이름</h4>
			<input type="text" name="name" onChange={getValue} />
			<h4>가게이미지(string)</h4>
			<input type="text" name="image" onChange={getValue} />
			<h4>가게 카테고리</h4>
			<div>
				<input
					type="checkbox"
					value="한식"
					onClick={(e) => {
						chngeHandler(
							e.currentTarget.checked,
							e.currentTarget.value
						);
					}}
					checked={checkedInput.includes("한식")}
				/>
				한식
				<input
					type="checkbox"
					value="분식"
					onClick={(e) => {
						chngeHandler(
							e.currentTarget.checked,
							e.currentTarget.value
						);
					}}
					checked={checkedInput.includes("분식")}
				/>
				분식
				<input
					type="checkbox"
					value="야식"
					onClick={(e) => {
						chngeHandler(
							e.currentTarget.checked,
							e.currentTarget.value
						);
					}}
					checked={checkedInput.includes("야식")}
				/>
				야식
				<input
					type="checkbox"
					value="양식"
					onClick={(e) => {
						chngeHandler(
							e.currentTarget.checked,
							e.currentTarget.value
						);
					}}
					checked={checkedInput.includes("양식")}
				/>
				양식
				<input
					type="checkbox"
					value="일식"
					onClick={(e) => {
						chngeHandler(
							e.currentTarget.checked,
							e.currentTarget.value
						);
					}}
					checked={checkedInput.includes("일식")}
				/>
				일식
				<input
					type="checkbox"
					value="중식"
					onClick={(e) => {
						chngeHandler(
							e.currentTarget.checked,
							e.currentTarget.value
						);
					}}
					checked={checkedInput.includes("중식")}
				/>
				중식
				<input
					type="checkbox"
					value="패스트푸드"
					onClick={(e) => {
						chngeHandler(
							e.currentTarget.checked,
							e.currentTarget.value
						);
					}}
					checked={checkedInput.includes("패스트푸드")}
				/>
				패스트푸드
				<input
					type="checkbox"
					value="치킨"
					onClick={(e) => {
						chngeHandler(
							e.currentTarget.checked,
							e.currentTarget.value
						);
					}}
					checked={checkedInput.includes("치킨")}
				/>
				치킨
				<input
					type="checkbox"
					value="피자"
					onClick={(e) => {
						chngeHandler(
							e.currentTarget.checked,
							e.currentTarget.value
						);
					}}
					checked={checkedInput.includes("피자")}
				/>
				피자
				<input
					type="checkbox"
					value="찜탕"
					onClick={(e) => {
						chngeHandler(
							e.currentTarget.checked,
							e.currentTarget.value
						);
					}}
					checked={checkedInput.includes("찜탕")}
				/>
				찜탕
				<input
					type="checkbox"
					value="디저트"
					onClick={(e) => {
						chngeHandler(
							e.currentTarget.checked,
							e.currentTarget.value
						);
					}}
					checked={checkedInput.includes("디저트")}
				/>
				디저트
			</div>
			<hr />
			<button type="button" onClick={post}>
				가게 만들기
			</button>
			<hr />
			<hr />
			<h1>-상품정보-</h1>
			<h4>상점아이디(number)</h4>
			<input type="text" name="shopId" onChange={changeId} />
			<h4>상품이름</h4>
			<input type="text" name="name" onChange={productPost} />
			<h4>상품가격</h4>
			<input type="text" name="price" onChange={productPost} />
			<h4>상품할인가격</h4>
			<input type="text" name="discountedPrice" onChange={productPost} />
			<h4>상품이미지(string)</h4>
			<input type="text" name="image" onChange={productPost} />
			<hr />
			<button type="button" onClick={post2}>
				상품 만들기
			</button>
			<hr />
			<hr />
		</DivSt>
	);
};

export default PostTest;
