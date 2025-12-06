import { fileURLToPath, URL } from "node:url";
import { copy, emptyDir, ensureDir, pathExists } from "fs-extra";

// 可以根据实际情况修改这个相对路径
// 源目录：项目下的 dist
const sourceDir = fileURLToPath(new URL("./dist", import.meta.url));
// 目标目录
const targetDir = fileURLToPath(new URL("../vita-admin/src/main/resources/static", import.meta.url));
(async () => {
	try {
		// 如果目标目录存在，先清空
		if (await pathExists(targetDir)) {
			await emptyDir(targetDir);
			console.log(`✅ 已清空目标目录: ${targetDir}`);
		} else {
			// 如果目标目录不存在，则创建
			await ensureDir(targetDir);
			console.log(`📁 已创建目标目录: ${targetDir}`);
		}

		// 复制目录
		await copy(sourceDir, targetDir);
		console.log(`📋 成功复制 ${sourceDir} 下的所有文件到 ${targetDir}`);
	} catch (err) {
		console.error("❌ 复制过程中发生错误:", err);
		process.exit(1);
	}
})();
