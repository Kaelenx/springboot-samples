<template>
  <view class="card" @click="go">
    <image
      v-if="showImg"
      class="cover"
      :src="String(item.cover)"
      mode="aspectFill"
      @error="imgErr = true"
    />
    <view v-else class="cover cover-ph">
      <text class="ph-text">{{ item.title.slice(0, 2) || "专" }}</text>
    </view>
    <view class="body">
      <text class="title">{{ item.title }}</text>
      <text v-if="item.introduction" class="intro">{{
        item.introduction
      }}</text>
      <text class="meta"
        >{{ item.updateLabel }} · {{ item.visitLabel }} ·
        {{ item.followersLabel }}</text
      >
      <view
        class="btn"
        :class="{ on: item.isFollowing }"
        @click.stop.prevent="noop"
      >
        {{ item.isFollowing ? "已关注" : "关注专题" }}
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { computed, ref, watch } from "vue";
import type { SpecialItem } from "@/types/special";

const props = defineProps<{ item: SpecialItem }>();

const imgErr = ref(false);
watch(
  () => [props.item.cover, props.item.id],
  () => {
    imgErr.value = false;
  },
);

const showImg = computed(() => Boolean(props.item.cover) && !imgErr.value);

function go() {
  const id = props.item.id;
  if (id === undefined || id === "") return;
  uni.navigateTo({
    url: `/pages/special/detail?id=${encodeURIComponent(String(id))}`,
  });
}

function noop() {
  /* 预留关注接口 */
}
</script>

<style scoped>
.card {
  display: flex;
  flex-direction: row;
  padding: 24rpx;
  margin: 0 24rpx 24rpx;
  background: #fff;
  border-radius: 12rpx;
  box-shadow: 0 2rpx 12rpx rgba(0, 0, 0, 0.06);
}
.cover {
  width: 200rpx;
  height: 160rpx;
  border-radius: 8rpx;
  background: #f0f0f0;
  flex-shrink: 0;
}
.cover-ph {
  display: flex;
  align-items: center;
  justify-content: center;
}
.ph-text {
  font-size: 36rpx;
  color: #8590a6;
}
.body {
  flex: 1;
  margin-left: 24rpx;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}
.title {
  font-size: 32rpx;
  font-weight: 600;
  color: #121212;
  margin-bottom: 12rpx;
}
.intro {
  font-size: 26rpx;
  color: #646464;
  margin-bottom: 12rpx;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  line-clamp: 2;
  -webkit-box-orient: vertical;
}
.meta {
  font-size: 24rpx;
  color: #8590a6;
  margin-bottom: 16rpx;
}
.btn {
  align-self: flex-start;
  padding: 8rpx 28rpx;
  font-size: 26rpx;
  border-radius: 999rpx;
  border: 1px solid #0084ff;
  color: #0084ff;
}
.btn.on {
  border-color: #ebebeb;
  color: #8590a6;
  background: #f6f6f6;
}
</style>
